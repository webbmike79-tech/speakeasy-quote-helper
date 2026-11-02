package com.example.model

import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * =========================================================================================
 * OWNER-REVIEW PLACEHOLDER HOUR RANGES TABLE (MIKE WEBB — SPEAKEASY SUPPORT)
 * =========================================================================================
 * Review and adjust all 10 problem category default hour ranges and recurring flags in this
 * single table before release. All values below are OWNER-REVIEW placeholders.
 *
 * NOTE: On-site visits automatically enforce the 1.0-hour minimum in StrictPricingEngine.
 * =========================================================================================
 */
object OwnerReviewHourPlaceholders {
    data class CategoryDefaults(
        val minHours: Double,
        val maxHours: Double,
        val defaultRecurring: Boolean,
        val plainScopeNote: String
    )

    // OWNER-REVIEW PLACEHOLDERS — Edit minHours, maxHours, and defaultRecurring here:
    val TABLE: Map<ProblemCategory, CategoryDefaults> = mapOf(
        ProblemCategory.SLOW_COMPUTER to CategoryDefaults(
            minHours = 1.0,
            maxHours = 2.0,
            defaultRecurring = false,
            plainScopeNote = "Startup optimization, disk health check, background process cleanup, and system tuning."
        ),
        ProblemCategory.VIRUS_MALWARE to CategoryDefaults(
            minHours = 1.5,
            maxHours = 3.0,
            defaultRecurring = false,
            plainScopeNote = "Deep malware/spyware removal, browser reset, security patching, and account safety check."
        ),
        ProblemCategory.WIFI_NETWORK to CategoryDefaults(
            minHours = 1.0,
            maxHours = 2.5,
            defaultRecurring = false,
            plainScopeNote = "Router/mesh diagnostics, dead-zone troubleshooting, DNS/channel tuning, and secure Wi-Fi setup."
        ),
        ProblemCategory.NEW_DEVICE_SETUP to CategoryDefaults(
            minHours = 1.0,
            maxHours = 2.5,
            defaultRecurring = false,
            plainScopeNote = "Unbox/configure new PC, Mac, tablet, or phone, user account setup, email, and file migration."
        ),
        ProblemCategory.PRINTER_PROBLEMS to CategoryDefaults(
            minHours = 1.0,
            maxHours = 1.5,
            defaultRecurring = false,
            plainScopeNote = "Driver reinstallation, wireless/network print queue repair, and scanner configuration."
        ),
        ProblemCategory.SMART_HOME_HELP to CategoryDefaults(
            minHours = 1.0,
            maxHours = 2.0,
            defaultRecurring = false,
            plainScopeNote = "Smart thermostats, cameras, doorbells, voice assistants, and Wi-Fi hub pairing."
        ),
        ProblemCategory.WORDPRESS_SITE_WORK to CategoryDefaults(
            minHours = 3.0,
            maxHours = 10.0,
            defaultRecurring = false,
            plainScopeNote = "WordPress site creation, host migration, theme/plugin customization, or SSL/speed fixes."
        ),
        ProblemCategory.BUSINESS_IT_ONGOING to CategoryDefaults(
            minHours = 2.0,
            maxHours = 6.0,
            defaultRecurring = true,
            plainScopeNote = "Ongoing workstation, network, printer, and software maintenance for small businesses and doctor-owned practices."
        ),
        ProblemCategory.PC_BUILD_INQUIRY to CategoryDefaults(
            minHours = 1.0,
            maxHours = 2.0,
            defaultRecurring = false,
            plainScopeNote = "Parts list consultation or post-purchase software setup. Hardware parts are not stocked; custom builds referred to Maverick Computers."
        ),
        ProblemCategory.SOMETHING_ELSE to CategoryDefaults(
            minHours = 1.0,
            maxHours = 2.5,
            defaultRecurring = false,
            plainScopeNote = "Custom IT issue — describe your problem in your own words below for a tailored preliminary hour range."
        )
    )
}

/**
 * =========================================================================================
 * STRICT AUTHORIZED PRICING CONSTANTS (NEVER INVENT OTHER PRICES, FEES, OR DISCOUNTS)
 * =========================================================================================
 */
object StrictPricingConstants {
    const val REMOTE_HOURLY_RATE = 65
    const val ONSITE_HOURLY_RATE = 85
    const val ONSITE_MINIMUM_HOURS = 1.0

    const val PLAN_HOME_MONTHLY = 49
    const val PLAN_SMALL_BUSINESS_MONTHLY = 199
    const val PLAN_PREMIUM_MONTHLY = 349

    const val DEPOSIT_THRESHOLD_DOLLARS = 500
    const val DEPOSIT_FRACTION = 0.50

    const val CANCELLATION_POLICY =
        "Cancellations with under 24 hours notice forfeit the trip fee from the deposit."

    const val OVER_500_DEPOSIT_POLICY =
        "Jobs over $500: 50% deposit to start, balance due on completion."

    // Exact mandatory word-for-word disclaimer — never alter a single character
    const val EXACT_DISCLAIMER = "Preliminary estimate — final pricing confirmed by Mike."

    // Contact Details
    const val PHONE_DISPLAY = "(239) 560-9061"
    const val PHONE_RAW = "2395609061"
    const val EMAIL_ADDRESS = "spkezspprt@gmail.com"
    const val WEBSITE_URL = "https://webbmike79-tech.github.io/speakeasy-support/"
}

enum class ProblemCategory(
    val displayTitle: String,
    val shortTag: String
) {
    SLOW_COMPUTER("Slow computer", "slow_computer"),
    VIRUS_MALWARE("Virus/malware cleanup", "virus_malware"),
    WIFI_NETWORK("Wi-Fi/network problems", "wifi_network"),
    NEW_DEVICE_SETUP("New computer or device setup", "new_device_setup"),
    PRINTER_PROBLEMS("Printer problems", "printer_problems"),
    SMART_HOME_HELP("Smart home help", "smart_home_help"),
    WORDPRESS_SITE_WORK("WordPress site work (create / migrate / customize)", "wordpress_site_work"),
    BUSINESS_IT_ONGOING("Business IT support (ongoing)", "business_it_ongoing"),
    PC_BUILD_INQUIRY("PC build inquiry", "pc_build_inquiry"),
    SOMETHING_ELSE("Something else", "something_else")
}

enum class CustomerType(val label: String) {
    HOME("Home"),
    BUSINESS("Business")
}

enum class ServiceMode(
    val label: String,
    val hourlyRate: Int,
    val rateSubtitle: String
) {
    REMOTE(
        label = "Remote",
        hourlyRate = StrictPricingConstants.REMOTE_HOURLY_RATE,
        rateSubtitle = "$65/hour • No minimum"
    ),
    ON_SITE(
        label = "On-site visit",
        hourlyRate = StrictPricingConstants.ONSITE_HOURLY_RATE,
        rateSubtitle = "$85/hour • 1-hour minimum"
    )
}

data class CustomProblemAnalysis(
    val userInputText: String,
    val plainSummary: String,
    val minHours: Double,
    val maxHours: Double,
    val isRecurring: Boolean,
    val isPcBuild: Boolean,
    val isOfflineFallback: Boolean
)

data class MonthlyPlanMathComparison(
    val recommendedPlanName: String,
    val recommendedPlanMonthlyCost: Int,
    val alternatePlanName: String?,
    val alternatePlanMonthlyCost: Int?,
    val singleVisitCostRange: String,
    val twoVisitsPerMonthCostRange: String,
    val mathLines: List<String>
)

data class QuoteEstimate(
    val problemCategory: ProblemCategory,
    val customerType: CustomerType,
    val serviceMode: ServiceMode,
    val isRecurring: Boolean,
    val effectiveMinHours: Double,
    val effectiveMaxHours: Double,
    val hourlyRate: Int,
    val minTotalDollars: Int,
    val maxTotalDollars: Int,
    val formattedHourRange: String,
    val formattedRateMath: String,
    val formattedTotalRange: String,
    val plainScopeSummary: String,
    val triggersOver500Deposit: Boolean,
    val depositMinDollars: Int,
    val depositMaxDollars: Int,
    val isPcBuildReferral: Boolean,
    val monthlyPlanComparison: MonthlyPlanMathComparison?,
    val customAnalysis: CustomProblemAnalysis?,
    val exactDisclaimer: String = StrictPricingConstants.EXACT_DISCLAIMER
)

object StrictPricingEngine {

    fun formatHours(hours: Double): String {
        val roundedHalf = (hours * 2.0).roundToInt() / 2.0
        return if (roundedHalf % 1.0 == 0.0) {
            "${roundedHalf.toInt()}"
        } else {
            "$roundedHalf"
        }
    }

    fun calculateQuote(
        problemCategory: ProblemCategory,
        customerType: CustomerType,
        serviceMode: ServiceMode,
        isRecurringToggleOn: Boolean,
        customAnalysis: CustomProblemAnalysis?
    ): QuoteEstimate {
        val defaults = OwnerReviewHourPlaceholders.TABLE.getValue(problemCategory)

        val rawMinHours = if (problemCategory == ProblemCategory.SOMETHING_ELSE && customAnalysis != null) {
            customAnalysis.minHours
        } else {
            defaults.minHours
        }
        val rawMaxHours = if (problemCategory == ProblemCategory.SOMETHING_ELSE && customAnalysis != null) {
            max(customAnalysis.minHours, customAnalysis.maxHours)
        } else {
            defaults.maxHours
        }

        // Enforce 1-hour minimum for On-site visit
        val effectiveMinHours = if (serviceMode == ServiceMode.ON_SITE) {
            max(StrictPricingConstants.ONSITE_MINIMUM_HOURS, rawMinHours)
        } else {
            max(0.5, rawMinHours)
        }
        val effectiveMaxHours = max(effectiveMinHours, rawMaxHours)

        val rate = serviceMode.hourlyRate
        val minTotalDollars = ceil(effectiveMinHours * rate).toInt()
        val maxTotalDollars = ceil(effectiveMaxHours * rate).toInt()

        val minHrsStr = formatHours(effectiveMinHours)
        val maxHrsStr = formatHours(effectiveMaxHours)
        val formattedHourRange = if (effectiveMinHours == effectiveMaxHours) {
            "$minHrsStr hour" + if (effectiveMinHours == 1.0) "" else "s"
        } else {
            "$minHrsStr–$maxHrsStr hours"
        }

        val formattedTotalRange = if (minTotalDollars == maxTotalDollars) {
            "$$minTotalDollars"
        } else {
            "$$minTotalDollars–$$maxTotalDollars"
        }

        val minNote = if (serviceMode == ServiceMode.ON_SITE) " (1-hour minimum)" else ""
        val formattedRateMath = "$formattedHourRange × $$rate/hour$minNote = $formattedTotalRange"

        val triggersOver500Deposit = maxTotalDollars > StrictPricingConstants.DEPOSIT_THRESHOLD_DOLLARS
        val depositMinDollars = ceil(minTotalDollars * StrictPricingConstants.DEPOSIT_FRACTION).toInt()
        val depositMaxDollars = ceil(maxTotalDollars * StrictPricingConstants.DEPOSIT_FRACTION).toInt()

        val effectiveRecurring = isRecurringToggleOn ||
            problemCategory == ProblemCategory.BUSINESS_IT_ONGOING ||
            (problemCategory == ProblemCategory.SOMETHING_ELSE && customAnalysis?.isRecurring == true)

        val isPcBuildReferral = problemCategory == ProblemCategory.PC_BUILD_INQUIRY ||
            (problemCategory == ProblemCategory.SOMETHING_ELSE && customAnalysis?.isPcBuild == true)

        val plainScopeSummary = if (problemCategory == ProblemCategory.SOMETHING_ELSE && customAnalysis != null) {
            customAnalysis.plainSummary
        } else {
            defaults.plainScopeNote
        }

        val monthlyPlanComparison = if (effectiveRecurring) {
            buildMonthlyPlanMath(
                customerType = customerType,
                serviceMode = serviceMode,
                effectiveMinHours = effectiveMinHours,
                effectiveMaxHours = effectiveMaxHours,
                minTotalDollars = minTotalDollars,
                maxTotalDollars = maxTotalDollars
            )
        } else {
            null
        }

        return QuoteEstimate(
            problemCategory = problemCategory,
            customerType = customerType,
            serviceMode = serviceMode,
            isRecurring = effectiveRecurring,
            effectiveMinHours = effectiveMinHours,
            effectiveMaxHours = effectiveMaxHours,
            hourlyRate = rate,
            minTotalDollars = minTotalDollars,
            maxTotalDollars = maxTotalDollars,
            formattedHourRange = formattedHourRange,
            formattedRateMath = formattedRateMath,
            formattedTotalRange = formattedTotalRange,
            plainScopeSummary = plainScopeSummary,
            triggersOver500Deposit = triggersOver500Deposit,
            depositMinDollars = depositMinDollars,
            depositMaxDollars = depositMaxDollars,
            isPcBuildReferral = isPcBuildReferral,
            monthlyPlanComparison = monthlyPlanComparison,
            customAnalysis = customAnalysis,
            exactDisclaimer = StrictPricingConstants.EXACT_DISCLAIMER
        )
    }

    private fun buildMonthlyPlanMath(
        customerType: CustomerType,
        serviceMode: ServiceMode,
        effectiveMinHours: Double,
        effectiveMaxHours: Double,
        minTotalDollars: Int,
        maxTotalDollars: Int
    ): MonthlyPlanMathComparison {
        val rate = serviceMode.hourlyRate
        val singleRange = if (minTotalDollars == maxTotalDollars) {
            "$$minTotalDollars"
        } else {
            "$$minTotalDollars–$$maxTotalDollars"
        }
        val twoVisitsMin = minTotalDollars * 2
        val twoVisitsMax = maxTotalDollars * 2
        val twoVisitsRange = if (twoVisitsMin == twoVisitsMax) {
            "$$twoVisitsMin/mo"
        } else {
            "$$twoVisitsMin–$$twoVisitsMax/mo"
        }

        val hrsText = if (effectiveMinHours == effectiveMaxHours) {
            "${formatHours(effectiveMinHours)} hr"
        } else {
            "${formatHours(effectiveMinHours)}–${formatHours(effectiveMaxHours)} hrs"
        }

        return if (customerType == CustomerType.HOME) {
            val homePlan = StrictPricingConstants.PLAN_HOME_MONTHLY
            MonthlyPlanMathComparison(
                recommendedPlanName = "Home Monthly Plan",
                recommendedPlanMonthlyCost = homePlan,
                alternatePlanName = null,
                alternatePlanMonthlyCost = null,
                singleVisitCostRange = singleRange,
                twoVisitsPerMonthCostRange = twoVisitsRange,
                mathLines = listOf(
                    "1 hourly visit ($hrsText × $$rate/hr): $singleRange",
                    "2 hourly visits in a month: $twoVisitsRange",
                    "Home Monthly Plan flat rate: $$homePlan/mo (less than 1 hour of hourly support)"
                )
            )
        } else {
            val sbPlan = StrictPricingConstants.PLAN_SMALL_BUSINESS_MONTHLY
            val premPlan = StrictPricingConstants.PLAN_PREMIUM_MONTHLY
            val threeVisitsMin = minTotalDollars * 3
            val threeVisitsMax = maxTotalDollars * 3
            val threeVisitsRange = if (threeVisitsMin == threeVisitsMax) {
                "$$threeVisitsMin/mo"
            } else {
                "$$threeVisitsMin–$$threeVisitsMax/mo"
            }
            val breakEvenSmallBizHours = String.format(java.util.Locale.US, "%.1f", sbPlan.toDouble() / rate.toDouble())
            val breakEvenPremiumHours = String.format(java.util.Locale.US, "%.1f", premPlan.toDouble() / rate.toDouble())

            MonthlyPlanMathComparison(
                recommendedPlanName = "Small Business Monthly Plan",
                recommendedPlanMonthlyCost = sbPlan,
                alternatePlanName = "Premium Monthly Plan (Doctor-Owned Practices / Multi-System)",
                alternatePlanMonthlyCost = premPlan,
                singleVisitCostRange = singleRange,
                twoVisitsPerMonthCostRange = twoVisitsRange,
                mathLines = listOf(
                    "1 hourly job ($hrsText × $$rate/hr): $singleRange",
                    "2 hourly jobs/month: $twoVisitsRange | 3 jobs/month: $threeVisitsRange",
                    "Small Business Plan: $$sbPlan/mo (equals ~$breakEvenSmallBizHours hrs at $$rate/hr)",
                    "Premium Plan (doctor-owned practices / heavier coverage): $$premPlan/mo (equals ~$breakEvenPremiumHours hrs at $$rate/hr)"
                )
            )
        }
    }

    /**
     * Deterministic offline keyword-based estimator used when Gemini times out (15-20s)
     * or when the device is offline. Never leaves the customer staring at a spinner.
     */
    fun estimateOfflineFallback(rawDescription: String): CustomProblemAnalysis {
        val cleaned = rawDescription.trim()
        val lower = cleaned.lowercase()

        val isPcBuild = lower.contains("pc build") ||
            lower.contains("build a pc") ||
            lower.contains("custom pc") ||
            lower.contains("gaming pc") ||
            lower.contains("graphics card") ||
            lower.contains("motherboard")

        val isRecurring = lower.contains("every day") ||
            lower.contains("every week") ||
            lower.contains("ongoing") ||
            lower.contains("monthly") ||
            lower.contains("keeps happening") ||
            lower.contains("repeatedly") ||
            lower.contains("practice") ||
            lower.contains("clinic") ||
            lower.contains("office network")

        val (minHours, maxHours) = when {
            isPcBuild -> 1.0 to 2.0
            lower.contains("wordpress") || lower.contains("website") || lower.contains("migrate site") -> 3.0 to 8.0
            lower.contains("server") || lower.contains("clinic") || lower.contains("multiple computers") || lower.contains("office") -> 2.5 to 6.0
            lower.contains("virus") || lower.contains("malware") || lower.contains("hacked") || lower.contains("scam") || lower.contains("ransomware") -> 1.5 to 3.0
            lower.contains("wifi") || lower.contains("wi-fi") || lower.contains("network") || lower.contains("router") || lower.contains("internet") -> 1.0 to 2.5
            lower.contains("printer") || lower.contains("scanner") -> 1.0 to 1.5
            else -> 1.0 to 2.5
        }

        val conciseEcho = if (cleaned.length > 160) {
            cleaned.take(157) + "..."
        } else {
            cleaned
        }

        val summary = "You described: \"$conciseEcho\" (Rough offline estimate based on your description)."

        return CustomProblemAnalysis(
            userInputText = cleaned,
            plainSummary = summary,
            minHours = minHours,
            maxHours = maxHours,
            isRecurring = isRecurring,
            isPcBuild = isPcBuild,
            isOfflineFallback = true
        )
    }

    /**
     * Formats a complete, plain-English quote summary suitable for SMS, Email, or Clipboard.
     */
    fun formatShareableSummary(quote: QuoteEstimate): String {
        val sb = StringBuilder()
        sb.appendLine("SPEAKEASY SUPPORT — PRELIMINARY QUOTE")
        sb.appendLine("=====================================")
        sb.appendLine("Problem: ${quote.problemCategory.displayTitle}")
        if (quote.problemCategory == ProblemCategory.SOMETHING_ELSE && quote.customAnalysis != null) {
            sb.appendLine("Issue Summary: ${quote.customAnalysis.plainSummary}")
        } else {
            sb.appendLine("Scope: ${quote.plainScopeSummary}")
        }
        sb.appendLine("Customer Type: ${quote.customerType.label}")
        sb.appendLine("Service Type: ${quote.serviceMode.label} ($${quote.hourlyRate}/hour)")
        sb.appendLine("Estimated Time: ${quote.formattedHourRange}")
        sb.appendLine("Preliminary Estimate: ${quote.formattedRateMath}")

        if (quote.triggersOver500Deposit) {
            val depRange = if (quote.depositMinDollars == quote.depositMaxDollars) {
                "$${quote.depositMinDollars}"
            } else {
                "$${quote.depositMinDollars}–$${quote.depositMaxDollars}"
            }
            sb.appendLine("50% Deposit Note: ${StrictPricingConstants.OVER_500_DEPOSIT_POLICY} (Estimated 50% deposit: $depRange)")
        }
        if (quote.serviceMode == ServiceMode.ON_SITE || quote.triggersOver500Deposit) {
            sb.appendLine("Cancellation Policy: ${StrictPricingConstants.CANCELLATION_POLICY}")
        }
        if (quote.isPcBuildReferral) {
            sb.appendLine("PC Build Note: Speakeasy Support does not stock hardware parts. For parts and custom system builds, we refer customers to Maverick Computers.")
        }
        quote.monthlyPlanComparison?.let { plan ->
            sb.appendLine()
            sb.appendLine("Monthly Plan Comparison (${plan.recommendedPlanName} — $${plan.recommendedPlanMonthlyCost}/mo):")
            plan.mathLines.forEach { line ->
                sb.appendLine("• $line")
            }
        }
        sb.appendLine()
        sb.appendLine(StrictPricingConstants.EXACT_DISCLAIMER)
        sb.appendLine("Contact Mike Webb: ${StrictPricingConstants.PHONE_DISPLAY} | ${StrictPricingConstants.EMAIL_ADDRESS}")
        sb.append("Website: ${StrictPricingConstants.WEBSITE_URL}")
        return sb.toString()
    }
}
