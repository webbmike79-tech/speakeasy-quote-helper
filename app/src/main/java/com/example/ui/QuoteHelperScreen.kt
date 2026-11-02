package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.model.ProblemCategory
import com.example.model.StrictPricingConstants
import com.example.model.StrictPricingEngine
import com.example.ui.theme.SpeakeasyCharcoal
import com.example.viewmodel.QuoteViewModel
import kotlinx.coroutines.launch

@Composable
fun QuoteHelperScreen(
    viewModel: QuoteViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val copiedMessage = stringResource(R.string.copied_toast)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = SpeakeasyCharcoal,
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .testTag("quote_helper_scroll_column"),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 1910s Chicago Speakeasy Masthead Banner
            SpeakeasyMastheadBanner()

            // STEP 1: PICK THE PROBLEM (10 exact options)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SectionFramingHeader(
                    stepLabel = stringResource(R.string.step_1_label),
                    title = stringResource(R.string.step_1_title),
                    subtitle = stringResource(R.string.step_1_subtitle)
                )
                Spacer(modifier = Modifier.height(2.dp))

                ProblemCategory.entries.forEach { category ->
                    ProblemCategoryButton(
                        category = category,
                        isSelected = uiState.selectedProblem == category,
                        onClick = { viewModel.selectProblem(category) }
                    )
                }

                // "Something else" Custom Problem Description + Gemini Estimator
                CustomProblemEstimatorCard(
                    visible = uiState.selectedProblem == ProblemCategory.SOMETHING_ELSE,
                    customInput = uiState.customProblemInput,
                    isAnalyzing = uiState.isAnalyzingCustomProblem,
                    validationError = uiState.customInputValidationError,
                    customAnalysis = uiState.customAnalysis,
                    onInputChanged = viewModel::updateCustomProblemInput,
                    onAnalyzeClicked = viewModel::analyzeCustomProblem
                )
            }

            // STEP 2: PICK CUSTOMER & SERVICE TYPE
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SectionFramingHeader(
                    stepLabel = stringResource(R.string.step_2_label),
                    title = stringResource(R.string.step_2_title),
                    subtitle = stringResource(R.string.step_2_subtitle)
                )
                CustomerAndServiceSelector(
                    selectedCustomerType = uiState.customerType,
                    selectedServiceMode = uiState.serviceMode,
                    isRecurring = uiState.isRecurring,
                    onCustomerTypeSelected = viewModel::selectCustomerType,
                    onServiceModeSelected = viewModel::selectServiceMode,
                    onRecurringToggled = viewModel::toggleRecurring
                )
            }

            // STEP 3: REVIEW PRELIMINARY ESTIMATE LEDGER
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SectionFramingHeader(
                    stepLabel = stringResource(R.string.step_3_label),
                    title = stringResource(R.string.step_3_title),
                    subtitle = stringResource(R.string.step_3_subtitle)
                )
                QuoteLedgerCard(quote = uiState.currentQuote)
            }

            // CONTACT CARD & ONE-TAP QUOTE SHARING
            SpeakeasyContactCard(
                onTextQuote = {
                    val summary = StrictPricingEngine.formatShareableSummary(uiState.currentQuote)
                    launchSmsIntent(context, summary)
                },
                onEmailQuote = {
                    val summary = StrictPricingEngine.formatShareableSummary(uiState.currentQuote)
                    launchEmailIntent(
                        context = context,
                        subject = "Speakeasy Support Quote Inquiry — ${uiState.currentQuote.problemCategory.displayTitle}",
                        body = summary
                    )
                },
                onCopyQuote = {
                    val summary = StrictPricingEngine.formatShareableSummary(uiState.currentQuote)
                    copyToClipboard(context, summary)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(copiedMessage)
                    }
                },
                onCallDirect = {
                    launchDialIntent(context)
                },
                onOpenWebsite = {
                    launchWebsiteIntent(context)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    val clip = ClipData.newPlainText("Speakeasy Support Quote", text)
    clipboard?.setPrimaryClip(clip)
}

private fun launchSmsIntent(context: Context, body: String) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:${StrictPricingConstants.PHONE_RAW}")
            putExtra("sms_body", body)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (_: Exception) {
        copyToClipboard(context, body)
    }
}

private fun launchEmailIntent(context: Context, subject: String, body: String) {
    try {
        val uri = Uri.parse(
            "mailto:${StrictPricingConstants.EMAIL_ADDRESS}" +
                "?subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(body)
        )
        val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (_: Exception) {
        copyToClipboard(context, body)
    }
}

private fun launchDialIntent(context: Context) {
    try {
        val intent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:${StrictPricingConstants.PHONE_RAW}")
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (_: Exception) {
        // Ignore if no dialer installed
    }
}

private fun launchWebsiteIntent(context: Context) {
    try {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(StrictPricingConstants.WEBSITE_URL)
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (_: Exception) {
        // Ignore if no browser installed
    }
}
