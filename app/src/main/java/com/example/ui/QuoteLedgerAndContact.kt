package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.MonthlyPlanMathComparison
import com.example.model.ProblemCategory
import com.example.model.QuoteEstimate
import com.example.model.StrictPricingConstants
import com.example.ui.theme.LedgerAlertBg
import com.example.ui.theme.LedgerAlertInk
import com.example.ui.theme.LedgerBorder
import com.example.ui.theme.LedgerInk
import com.example.ui.theme.LedgerInkSecondary
import com.example.ui.theme.LedgerPaper
import com.example.ui.theme.LedgerPaperCallout
import com.example.ui.theme.LedgerPaperSubtle
import com.example.ui.theme.LedgerPlanBg
import com.example.ui.theme.LedgerPlanBorder
import com.example.ui.theme.LedgerPlanInk
import com.example.ui.theme.PlayfairDisplayFamily
import com.example.ui.theme.PlusJakartaSansFamily
import com.example.ui.theme.RobotoMonoFamily
import com.example.ui.theme.SpeakeasyBrass
import com.example.ui.theme.SpeakeasyBrassLight
import com.example.ui.theme.SpeakeasyBronze
import com.example.ui.theme.SpeakeasyCard
import com.example.ui.theme.SpeakeasyCardElevated
import com.example.ui.theme.SpeakeasyCharcoal
import com.example.ui.theme.SpeakeasyTextPrimary
import com.example.ui.theme.SpeakeasyTextSecondary

@Composable
fun QuoteLedgerCard(
    quote: QuoteEstimate,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("quote_ledger_card"),
        color = LedgerPaper,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(2.dp, SpeakeasyBrass)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Ledger Header
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = stringResource(R.string.ledger_header_badge),
                    fontFamily = RobotoMonoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = LedgerInkSecondary
                )
                Text(
                    text = quote.problemCategory.displayTitle,
                    fontFamily = PlayfairDisplayFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = LedgerInk
                )
                Text(
                    text = quote.plainScopeSummary,
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = LedgerInkSecondary
                )
            }

            HorizontalDivider(thickness = 1.5.dp, color = LedgerBorder)

            // Itemized Selections & Rate Breakdown
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                LedgerKeyValueRow(
                    label = "Customer Type",
                    value = quote.customerType.label
                )
                LedgerKeyValueRow(
                    label = "Service Format",
                    value = "${quote.serviceMode.label} ($${quote.hourlyRate}/hour)"
                )
                LedgerKeyValueRow(
                    label = "Estimated Time Window",
                    value = quote.formattedHourRange
                )
                LedgerKeyValueRow(
                    label = "Hourly Calculation",
                    value = quote.formattedRateMath
                )
            }

            // Primary Total Estimate Box
            Surface(
                color = LedgerPaperCallout,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.5.dp, LedgerInk),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("preliminary_total_box")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = if (quote.isPcBuildReferral) {
                            "PRELIMINARY CONSULTATION / SETUP LABOR ESTIMATE"
                        } else {
                            "PRELIMINARY LABOR ESTIMATE"
                        },
                        fontFamily = RobotoMonoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 0.8.sp,
                        color = LedgerInkSecondary,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = quote.formattedTotalRange,
                        fontFamily = RobotoMonoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp,
                        color = LedgerInk,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("preliminary_total_amount")
                    )
                    Text(
                        text = quote.formattedRateMath,
                        fontFamily = RobotoMonoFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = LedgerInkSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // PC Build Inquiry Advisory & Maverick Computers Referral
            AnimatedVisibility(visible = quote.isPcBuildReferral) {
                PcBuildMaverickReferralBox()
            }

            // Jobs over $500 Deposit Alert Callout
            AnimatedVisibility(visible = quote.triggersOver500Deposit) {
                Over500DepositCallout(
                    depositMin = quote.depositMinDollars,
                    depositMax = quote.depositMaxDollars
                )
            }

            // Smarter Deal: Monthly Plan Comparison Callout
            AnimatedVisibility(visible = quote.monthlyPlanComparison != null) {
                quote.monthlyPlanComparison?.let { planMath ->
                    MonthlyPlanComparisonCallout(planMath = planMath)
                }
            }

            HorizontalDivider(thickness = 1.dp, color = LedgerBorder)

            // Standard Pricing & Policy Reference Block (Plain English, zero slang)
            Surface(
                color = LedgerPaperSubtle,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, LedgerBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = LedgerInk,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = stringResource(R.string.policy_heading),
                            fontFamily = PlusJakartaSansFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = LedgerInk
                        )
                    }
                    Text(
                        text = stringResource(R.string.policy_remote_rate),
                        fontFamily = RobotoMonoFamily,
                        fontSize = 12.sp,
                        color = LedgerInk
                    )
                    Text(
                        text = stringResource(R.string.policy_onsite_rate),
                        fontFamily = RobotoMonoFamily,
                        fontSize = 12.sp,
                        color = LedgerInk
                    )
                    Text(
                        text = stringResource(R.string.policy_monthly_plans),
                        fontFamily = RobotoMonoFamily,
                        fontSize = 12.sp,
                        color = LedgerInk
                    )
                    Text(
                        text = stringResource(R.string.policy_over_500),
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 13.sp,
                        color = LedgerInk
                    )
                    Text(
                        text = stringResource(R.string.policy_cancellation),
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 13.sp,
                        color = LedgerInk
                    )
                }
            }

            // Mandatory Word-for-Word Disclaimer Box
            Surface(
                color = LedgerInk,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.5.dp, SpeakeasyBrass),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mandatory_disclaimer_box")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = SpeakeasyBrass,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = StrictPricingConstants.EXACT_DISCLAIMER,
                        fontFamily = PlusJakartaSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = LedgerPaper,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("mandatory_disclaimer_text")
                    )
                }
            }
        }
    }
}

@Composable
private fun LedgerKeyValueRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontFamily = PlusJakartaSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = LedgerInkSecondary,
            modifier = Modifier.weight(0.42f)
        )
        Text(
            text = value,
            fontFamily = RobotoMonoFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = LedgerInk,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.58f)
        )
    }
}

@Composable
private fun PcBuildMaverickReferralBox() {
    Surface(
        color = LedgerAlertBg,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.5.dp, LedgerAlertInk),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("pc_build_referral_box")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Memory,
                    contentDescription = null,
                    tint = LedgerAlertInk,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = "PC Build Policy & Maverick Computers Referral",
                    fontFamily = PlusJakartaSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = LedgerAlertInk
                )
            }
            Text(
                text = "Speakeasy Support does not stock hardware parts. For purchasing components and full custom PC builds, we refer customers to Maverick Computers.",
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = LedgerInk
            )
            Text(
                text = "Rough Market Hardware Cost Ideas (Reference Only — Parts Not Sold Here):",
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = LedgerAlertInk
            )
            Text(
                text = "• Home / Office Desktop Parts: ~$500–$850\n" +
                    "• Mainstream Gaming / Creator Parts: ~$1,000–$1,600\n" +
                    "• High-End Workstation / Enthusiast Parts: ~$1,800–$3,000+\n" +
                    "• Speakeasy Support Consultation / OS & Data Setup Labor: billed strictly at $65/hr remote or $85/hr on-site (1-hour minimum).",
                fontFamily = RobotoMonoFamily,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = LedgerInk
            )
        }
    }
}

@Composable
private fun Over500DepositCallout(
    depositMin: Int,
    depositMax: Int
) {
    val depositRangeText = if (depositMin == depositMax) {
        "$$depositMin"
    } else {
        "$$depositMin–$$depositMax"
    }
    Surface(
        color = LedgerAlertBg,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.5.dp, LedgerAlertInk),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("over_500_deposit_box")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.WarningAmber,
                    contentDescription = null,
                    tint = LedgerAlertInk,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Jobs Over $500 — Deposit & Cancellation Policy",
                    fontFamily = PlusJakartaSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = LedgerAlertInk
                )
            }
            Text(
                text = StrictPricingConstants.OVER_500_DEPOSIT_POLICY,
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = LedgerInk
            )
            Text(
                text = "50% Deposit Math on This Estimate: $depositRangeText to start, with the remaining 50% balance ($depositRangeText) due on completion.",
                fontFamily = RobotoMonoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = LedgerInk
            )
            Text(
                text = StrictPricingConstants.CANCELLATION_POLICY,
                fontFamily = PlusJakartaSansFamily,
                fontSize = 13.sp,
                color = LedgerInk
            )
        }
    }
}

@Composable
private fun MonthlyPlanComparisonCallout(
    planMath: MonthlyPlanMathComparison
) {
    Surface(
        color = LedgerPlanBg,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.5.dp, LedgerPlanBorder),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("monthly_plan_comparison_box")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = null,
                    tint = LedgerPlanInk,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = "Smarter Deal: Monthly Plan Comparison",
                    fontFamily = PlusJakartaSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = LedgerPlanInk
                )
            }

            Text(
                text = "For recurring issues or ongoing support, here is the side-by-side math comparing pay-per-incident hourly billing with our monthly plans:",
                fontFamily = PlusJakartaSansFamily,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = LedgerInk
            )

            Surface(
                color = LedgerPaper,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, LedgerPlanBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = planMath.recommendedPlanName,
                            fontFamily = PlusJakartaSansFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = LedgerPlanInk
                        )
                        Text(
                            text = "$${planMath.recommendedPlanMonthlyCost}/mo",
                            fontFamily = RobotoMonoFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = LedgerPlanInk
                        )
                    }
                    if (planMath.alternatePlanName != null && planMath.alternatePlanMonthlyCost != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = planMath.alternatePlanName,
                                fontFamily = PlusJakartaSansFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                color = LedgerInk,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "$${planMath.alternatePlanMonthlyCost}/mo",
                                fontFamily = RobotoMonoFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = LedgerInk
                            )
                        }
                    }

                    HorizontalDivider(thickness = 1.dp, color = LedgerBorder)

                    planMath.mathLines.forEach { line ->
                        Text(
                            text = "• $line",
                            fontFamily = RobotoMonoFamily,
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            color = LedgerInk
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SpeakeasyContactCard(
    onTextQuote: () -> Unit,
    onEmailQuote: () -> Unit,
    onCopyQuote: () -> Unit,
    onCallDirect: () -> Unit,
    onOpenWebsite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("contact_card"),
        color = SpeakeasyCard,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.5.dp, SpeakeasyBrass)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.contact_section_label),
                fontFamily = RobotoMonoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                letterSpacing = 1.sp,
                color = SpeakeasyBrass
            )
            Text(
                text = stringResource(R.string.contact_card_title),
                fontFamily = PlayfairDisplayFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = SpeakeasyTextPrimary
            )
            Text(
                text = stringResource(R.string.contact_card_subtitle),
                fontFamily = PlusJakartaSansFamily,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = SpeakeasyTextSecondary
            )

            // Contact info overview box
            Surface(
                color = SpeakeasyCharcoal,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, SpeakeasyBronze),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Call or Text: ${StrictPricingConstants.PHONE_DISPLAY}",
                        fontFamily = RobotoMonoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = SpeakeasyBrassLight
                    )
                    Text(
                        text = "Email: ${StrictPricingConstants.EMAIL_ADDRESS}",
                        fontFamily = RobotoMonoFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = SpeakeasyTextPrimary
                    )
                    Text(
                        text = "Web: ${StrictPricingConstants.WEBSITE_URL}",
                        fontFamily = RobotoMonoFamily,
                        fontSize = 12.sp,
                        color = SpeakeasyTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Primary Action 1: Pre-fill Text Message with Quote Summary
            Button(
                onClick = onTextQuote,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .testTag("btn_text_quote"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SpeakeasyBrass,
                    contentColor = SpeakeasyCharcoal
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Sms,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.btn_text_quote),
                    fontFamily = PlusJakartaSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // Primary Action 2: Pre-fill Email with Quote Summary
            Button(
                onClick = onEmailQuote,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .testTag("btn_email_quote"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SpeakeasyCardElevated,
                    contentColor = SpeakeasyBrassLight
                ),
                border = BorderStroke(1.5.dp, SpeakeasyBrass)
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.btn_email_quote),
                    fontFamily = PlusJakartaSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            // Action 3: Copy Quote Summary to Clipboard
            OutlinedButton(
                onClick = onCopyQuote,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .testTag("btn_copy_quote"),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.5.dp, SpeakeasyBronze),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = SpeakeasyTextPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    tint = SpeakeasyBrass,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.btn_copy_quote),
                    fontFamily = PlusJakartaSansFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }

            // Action 4 & 5: Direct Call & Website
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onCallDirect,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 56.dp)
                        .testTag("btn_call_direct"),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, SpeakeasyBronze),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = SpeakeasyTextPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        tint = SpeakeasyBrass,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Call Mike",
                        fontFamily = PlusJakartaSansFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

                OutlinedButton(
                    onClick = onOpenWebsite,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 56.dp)
                        .testTag("btn_visit_website"),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, SpeakeasyBronze),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = SpeakeasyTextPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = null,
                        tint = SpeakeasyBrass,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Website",
                        fontFamily = PlusJakartaSansFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
