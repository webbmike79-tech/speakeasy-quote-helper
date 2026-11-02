package com.example.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.CustomProblemAnalysis
import com.example.model.CustomerType
import com.example.model.ProblemCategory
import com.example.model.QuoteEstimate
import com.example.model.ServiceMode
import com.example.model.StrictPricingEngine
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Schema
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.json.JSONObject
import kotlin.math.max
import kotlin.math.min

private const val TAG = "QuoteViewModel"

data class QuoteUiState(
    val selectedProblem: ProblemCategory = ProblemCategory.SLOW_COMPUTER,
    val customerType: CustomerType = CustomerType.HOME,
    val serviceMode: ServiceMode = ServiceMode.ON_SITE,
    val isRecurring: Boolean = false,
    val customProblemInput: String = "",
    val isAnalyzingCustomProblem: Boolean = false,
    val customAnalysis: CustomProblemAnalysis? = null,
    val customInputValidationError: String? = null,
    val currentQuote: QuoteEstimate = StrictPricingEngine.calculateQuote(
        problemCategory = ProblemCategory.SLOW_COMPUTER,
        customerType = CustomerType.HOME,
        serviceMode = ServiceMode.ON_SITE,
        isRecurringToggleOn = false,
        customAnalysis = null
    )
)

class QuoteViewModel : ViewModel() {

    // Structured JSON schema returning ONLY summary, minHours, maxHours, isRecurring, isPcBuild.
    // All dollar calculations happen deterministically in Kotlin.
    private val customEstimateSchema = Schema.obj(
        properties = mapOf(
            "summary" to Schema.string(
                description = "A concise, crystal-clear plain-English summary of what the customer typed (1-2 sentences, zero slang, never include dollar signs or prices)."
            ),
            "minHours" to Schema.double(
                description = "Realistic minimum hours needed for an IT support technician (between 0.5 and 12.0)."
            ),
            "maxHours" to Schema.double(
                description = "Realistic maximum hours needed for an IT support technician (between 1.0 and 20.0, must be >= minHours)."
            ),
            "isRecurring" to Schema.boolean(
                description = "True if the issue sounds ongoing, repeated, or involves regular business/medical practice IT maintenance."
            ),
            "isPcBuild" to Schema.boolean(
                description = "True if the customer is asking about buying parts for or assembling a custom PC build."
            )
        )
    )

    private val generativeModel by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = customEstimateSchema
                temperature = 0.2f
            },
            systemInstruction = content {
                text(
                    "You are an IT support triage assistant for Speakeasy Support in Millsap, TX. " +
                        "Summarize the customer's technical issue clearly in plain English with zero slang. " +
                        "NEVER mention dollar amounts, prices, discounts, or military/veteran backgrounds. " +
                        "Only output the JSON object with summary, minHours, maxHours, isRecurring, and isPcBuild."
                )
            }
        )
    }

    private val _uiState = MutableStateFlow(QuoteUiState())
    val uiState: StateFlow<QuoteUiState> = _uiState.asStateFlow()

    fun selectProblem(category: ProblemCategory) {
        _uiState.update { prev ->
            // Auto-default customerType to BUSINESS and recurring to true when Business IT support (ongoing) is chosen
            val nextCustomerType = if (category == ProblemCategory.BUSINESS_IT_ONGOING) {
                CustomerType.BUSINESS
            } else {
                prev.customerType
            }
            val nextRecurring = if (category == ProblemCategory.BUSINESS_IT_ONGOING) {
                true
            } else {
                prev.isRecurring
            }

            // If switching to Something Else and user already typed text, keep existing customAnalysis if any
            val nextEstimate = StrictPricingEngine.calculateQuote(
                problemCategory = category,
                customerType = nextCustomerType,
                serviceMode = prev.serviceMode,
                isRecurringToggleOn = nextRecurring,
                customAnalysis = prev.customAnalysis
            )

            prev.copy(
                selectedProblem = category,
                customerType = nextCustomerType,
                isRecurring = nextRecurring,
                customInputValidationError = null,
                currentQuote = nextEstimate
            )
        }
    }

    fun selectCustomerType(customerType: CustomerType) {
        _uiState.update { prev ->
            val nextEstimate = StrictPricingEngine.calculateQuote(
                problemCategory = prev.selectedProblem,
                customerType = customerType,
                serviceMode = prev.serviceMode,
                isRecurringToggleOn = prev.isRecurring,
                customAnalysis = prev.customAnalysis
            )
            prev.copy(
                customerType = customerType,
                currentQuote = nextEstimate
            )
        }
    }

    fun selectServiceMode(serviceMode: ServiceMode) {
        _uiState.update { prev ->
            val nextEstimate = StrictPricingEngine.calculateQuote(
                problemCategory = prev.selectedProblem,
                customerType = prev.customerType,
                serviceMode = serviceMode,
                isRecurringToggleOn = prev.isRecurring,
                customAnalysis = prev.customAnalysis
            )
            prev.copy(
                serviceMode = serviceMode,
                currentQuote = nextEstimate
            )
        }
    }

    fun toggleRecurring(isRecurring: Boolean) {
        _uiState.update { prev ->
            val nextEstimate = StrictPricingEngine.calculateQuote(
                problemCategory = prev.selectedProblem,
                customerType = prev.customerType,
                serviceMode = prev.serviceMode,
                isRecurringToggleOn = isRecurring,
                customAnalysis = prev.customAnalysis
            )
            prev.copy(
                isRecurring = isRecurring,
                currentQuote = nextEstimate
            )
        }
    }

    fun updateCustomProblemInput(input: String) {
        _uiState.update { prev ->
            prev.copy(
                customProblemInput = input,
                customInputValidationError = null
            )
        }
    }

    /**
     * Analyzes the customer's "Something else" problem description via Firebase AI Logic
     * (gemini-3.8-flash) with an 18-second max timeout, falling back immediately to the
     * deterministic offline keyword estimator if offline or timed out.
     */
    fun analyzeCustomProblem() {
        val description = _uiState.value.customProblemInput.trim()
        if (description.isBlank()) {
            _uiState.update {
                it.copy(customInputValidationError = "Please describe your problem in a few words first.")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedProblem = ProblemCategory.SOMETHING_ELSE,
                    isAnalyzingCustomProblem = true,
                    customInputValidationError = null
                )
            }

            val analysis: CustomProblemAnalysis = try {
                // 18-second timeout (within 15-20s requirement) before instant offline fallback
                val response = withTimeout(18_000L) {
                    generativeModel.generateContent(
                        "Customer problem description: \"$description\""
                    )
                }
                val jsonText = response.text
                if (jsonText.isNullOrBlank()) {
                    throw IllegalStateException("Empty AI response")
                }

                val json = JSONObject(jsonText)
                val rawSummary = json.optString("summary", "").trim()
                val rawMin = json.optDouble("minHours", 1.0)
                val rawMax = json.optDouble("maxHours", 2.5)
                val isRecurring = json.optBoolean("isRecurring", false)
                val isPcBuild = json.optBoolean("isPcBuild", false)

                val clampedMin = min(16.0, max(0.5, rawMin))
                val clampedMax = min(24.0, max(clampedMin, rawMax))
                val safeSummary = if (rawSummary.isNotBlank()) {
                    rawSummary
                } else {
                    "You described: \"$description\""
                }

                CustomProblemAnalysis(
                    userInputText = description,
                    plainSummary = safeSummary,
                    minHours = clampedMin,
                    maxHours = clampedMax,
                    isRecurring = isRecurring,
                    isPcBuild = isPcBuild,
                    isOfflineFallback = false
                )
            } catch (e: CancellationException) {
                if (e is TimeoutCancellationException) {
                    Log.w(TAG, "AI estimate timed out after 18s; using deterministic offline fallback", e)
                    StrictPricingEngine.estimateOfflineFallback(description)
                } else {
                    throw e
                }
            } catch (e: Exception) {
                Log.w(TAG, "AI estimate unavailable; using deterministic offline fallback", e)
                StrictPricingEngine.estimateOfflineFallback(description)
            }

            _uiState.update { prev ->
                val nextRecurring = prev.isRecurring || analysis.isRecurring
                val nextEstimate = StrictPricingEngine.calculateQuote(
                    problemCategory = ProblemCategory.SOMETHING_ELSE,
                    customerType = prev.customerType,
                    serviceMode = prev.serviceMode,
                    isRecurringToggleOn = nextRecurring,
                    customAnalysis = analysis
                )
                prev.copy(
                    isAnalyzingCustomProblem = false,
                    isRecurring = nextRecurring,
                    customAnalysis = analysis,
                    currentQuote = nextEstimate
                )
            }
        }
    }
}
