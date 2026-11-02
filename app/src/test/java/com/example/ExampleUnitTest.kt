package com.example

import com.example.model.CustomProblemAnalysis
import com.example.model.CustomerType
import com.example.model.OwnerReviewHourPlaceholders
import com.example.model.ProblemCategory
import com.example.model.ServiceMode
import com.example.model.StrictPricingConstants
import com.example.model.StrictPricingEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun allTenProblemCategoriesHaveOwnerReviewDefaults() {
        assertEquals(10, ProblemCategory.entries.size)
        ProblemCategory.entries.forEach { category ->
            val defaults = OwnerReviewHourPlaceholders.TABLE[category]
            assertNotNull("Missing default for $category", defaults)
            assertTrue(defaults!!.minHours > 0.0)
            assertTrue(defaults.maxHours >= defaults.minHours)
        }
    }

    @Test
    fun remoteAndOnSiteRatesAndOneHourMinimumAreEnforced() {
        // Sub-1-hour custom analysis on Remote vs On-Site
        val halfHourAnalysis = CustomProblemAnalysis(
            userInputText = "Quick password check",
            plainSummary = "Quick password check",
            minHours = 0.5,
            maxHours = 0.5,
            isRecurring = false,
            isPcBuild = false,
            isOfflineFallback = false
        )

        val remoteQuote = StrictPricingEngine.calculateQuote(
            problemCategory = ProblemCategory.SOMETHING_ELSE,
            customerType = CustomerType.HOME,
            serviceMode = ServiceMode.REMOTE,
            isRecurringToggleOn = false,
            customAnalysis = halfHourAnalysis
        )
        assertEquals(65, remoteQuote.hourlyRate)
        assertEquals(0.5, remoteQuote.effectiveMinHours, 0.001)
        assertEquals("Preliminary estimate — final pricing confirmed by Mike.", remoteQuote.exactDisclaimer)

        val onSiteQuote = StrictPricingEngine.calculateQuote(
            problemCategory = ProblemCategory.SOMETHING_ELSE,
            customerType = CustomerType.HOME,
            serviceMode = ServiceMode.ON_SITE,
            isRecurringToggleOn = false,
            customAnalysis = halfHourAnalysis
        )
        assertEquals(85, onSiteQuote.hourlyRate)
        // On-site enforces 1-hour minimum
        assertEquals(1.0, onSiteQuote.effectiveMinHours, 0.001)
        assertEquals(85, onSiteQuote.minTotalDollars)
        assertEquals("Preliminary estimate — final pricing confirmed by Mike.", onSiteQuote.exactDisclaimer)
    }

    @Test
    fun jobsOver500TriggerFiftyPercentDepositMath() {
        // WordPress site work on-site (3-10 hours @ $85/hr = $255-$850, which exceeds $500)
        val wpQuote = StrictPricingEngine.calculateQuote(
            problemCategory = ProblemCategory.WORDPRESS_SITE_WORK,
            customerType = CustomerType.BUSINESS,
            serviceMode = ServiceMode.ON_SITE,
            isRecurringToggleOn = false,
            customAnalysis = null
        )
        assertTrue(wpQuote.triggersOver500Deposit)
        assertEquals(850, wpQuote.maxTotalDollars)
        assertEquals(425, wpQuote.depositMaxDollars)
    }

    @Test
    fun recurringAndBusinessItTriggerMonthlyPlanComparisonMath() {
        val homeNotRecurring = StrictPricingEngine.calculateQuote(
            problemCategory = ProblemCategory.PRINTER_PROBLEMS,
            customerType = CustomerType.HOME,
            serviceMode = ServiceMode.REMOTE,
            isRecurringToggleOn = false,
            customAnalysis = null
        )
        assertNull(homeNotRecurring.monthlyPlanComparison)

        val homeRecurring = StrictPricingEngine.calculateQuote(
            problemCategory = ProblemCategory.PRINTER_PROBLEMS,
            customerType = CustomerType.HOME,
            serviceMode = ServiceMode.REMOTE,
            isRecurringToggleOn = true,
            customAnalysis = null
        )
        assertNotNull(homeRecurring.monthlyPlanComparison)
        assertEquals(StrictPricingConstants.PLAN_HOME_MONTHLY, homeRecurring.monthlyPlanComparison?.recommendedPlanMonthlyCost)

        val businessOngoing = StrictPricingEngine.calculateQuote(
            problemCategory = ProblemCategory.BUSINESS_IT_ONGOING,
            customerType = CustomerType.BUSINESS,
            serviceMode = ServiceMode.ON_SITE,
            isRecurringToggleOn = false,
            customAnalysis = null
        )
        assertTrue(businessOngoing.isRecurring)
        assertNotNull(businessOngoing.monthlyPlanComparison)
        assertEquals(StrictPricingConstants.PLAN_SMALL_BUSINESS_MONTHLY, businessOngoing.monthlyPlanComparison?.recommendedPlanMonthlyCost)
        assertEquals(StrictPricingConstants.PLAN_PREMIUM_MONTHLY, businessOngoing.monthlyPlanComparison?.alternatePlanMonthlyCost)
    }

    @Test
    fun pcBuildInquiryTriggersMaverickReferral() {
        val pcQuote = StrictPricingEngine.calculateQuote(
            problemCategory = ProblemCategory.PC_BUILD_INQUIRY,
            customerType = CustomerType.HOME,
            serviceMode = ServiceMode.REMOTE,
            isRecurringToggleOn = false,
            customAnalysis = null
        )
        assertTrue(pcQuote.isPcBuildReferral)
        val shareText = StrictPricingEngine.formatShareableSummary(pcQuote)
        assertTrue(shareText.contains("Maverick Computers"))
        assertTrue(shareText.contains("Preliminary estimate — final pricing confirmed by Mike."))
        assertFalse(shareText.lowercase().contains("military"))
        assertFalse(shareText.lowercase().contains("veteran"))
    }
}
