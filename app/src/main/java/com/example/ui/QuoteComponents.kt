package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DevicesOther
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CustomProblemAnalysis
import com.example.model.CustomerType
import com.example.model.OwnerReviewHourPlaceholders
import com.example.model.ProblemCategory
import com.example.model.ServiceMode
import com.example.model.StrictPricingEngine
import com.example.ui.theme.PlayfairDisplayFamily
import com.example.ui.theme.PlusJakartaSansFamily
import com.example.ui.theme.RobotoMonoFamily
import com.example.ui.theme.SpeakeasyBrass
import com.example.ui.theme.SpeakeasyBrassLight
import com.example.ui.theme.SpeakeasyBronze
import com.example.ui.theme.SpeakeasyCard
import com.example.ui.theme.SpeakeasyCardElevated
import com.example.ui.theme.SpeakeasyCharcoal
import com.example.ui.theme.SpeakeasyTextMuted
import com.example.ui.theme.SpeakeasyTextPrimary
import com.example.ui.theme.SpeakeasyTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SpeakeasyMastheadBanner(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("masthead_banner"),
        color = SpeakeasyCard,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.5.dp, SpeakeasyBrass)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            SpeakeasyCardElevated,
                            SpeakeasyCard,
                            SpeakeasyCharcoal
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Top Art Deco rule
                ArtDecoDivider()
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.masthead_location),
                    fontFamily = RobotoMonoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    letterSpacing = 1.2.sp,
                    color = SpeakeasyBrass,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_speakeasy_crest),
                    contentDescription = "Speakeasy Support Crest",
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.masthead_title),
                    fontFamily = PlayfairDisplayFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    letterSpacing = 1.5.sp,
                    color = SpeakeasyTextPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = stringResource(R.string.masthead_tagline),
                    fontFamily = PlayfairDisplayFamily,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    color = SpeakeasyBrassLight,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.masthead_subtitle),
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = SpeakeasyTextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Service area pills
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("Stephenville", "Millsap", "Parker County", "West Fort Worth").forEach { town ->
                        Surface(
                            color = SpeakeasyCharcoal,
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(1.dp, SpeakeasyBronze)
                        ) {
                            Text(
                                text = town,
                                fontFamily = RobotoMonoFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = SpeakeasyBrassLight,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                ArtDecoDivider()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.masthead_owner_badge),
                    fontFamily = RobotoMonoFamily,
                    fontSize = 10.sp,
                    letterSpacing = 0.8.sp,
                    color = SpeakeasyTextMuted,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ArtDecoDivider(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
    ) {
        val midY = size.height / 2f
        val centerX = size.width / 2f
        drawLine(
            color = SpeakeasyBronze,
            start = Offset(0f, midY),
            end = Offset(centerX - 24f, midY),
            strokeWidth = 2f
        )
        drawCircle(
            color = SpeakeasyBrass,
            radius = 4f,
            center = Offset(centerX, midY)
        )
        drawLine(
            color = SpeakeasyBronze,
            start = Offset(centerX + 24f, midY),
            end = Offset(size.width, midY),
            strokeWidth = 2f
        )
    }
}

@Composable
fun SectionFramingHeader(
    stepLabel: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                color = SpeakeasyCardElevated,
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, SpeakeasyBrass)
            ) {
                Text(
                    text = stepLabel,
                    fontFamily = RobotoMonoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = SpeakeasyBrass,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = SpeakeasyBronze
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontFamily = PlayfairDisplayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = SpeakeasyTextPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            fontFamily = PlusJakartaSansFamily,
            fontSize = 14.sp,
            color = SpeakeasyTextSecondary
        )
    }
}

fun iconForProblem(category: ProblemCategory): ImageVector = when (category) {
    ProblemCategory.SLOW_COMPUTER -> Icons.Default.Speed
    ProblemCategory.VIRUS_MALWARE -> Icons.Default.Security
    ProblemCategory.WIFI_NETWORK -> Icons.Default.Wifi
    ProblemCategory.NEW_DEVICE_SETUP -> Icons.Default.Computer
    ProblemCategory.PRINTER_PROBLEMS -> Icons.Default.Print
    ProblemCategory.SMART_HOME_HELP -> Icons.Default.HomeWork
    ProblemCategory.WORDPRESS_SITE_WORK -> Icons.Default.Language
    ProblemCategory.BUSINESS_IT_ONGOING -> Icons.Default.Business
    ProblemCategory.PC_BUILD_INQUIRY -> Icons.Default.Memory
    ProblemCategory.SOMETHING_ELSE -> Icons.Default.EditNote
}

@Composable
fun ProblemCategoryButton(
    category: ProblemCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val defaults = OwnerReviewHourPlaceholders.TABLE.getValue(category)
    val hrsPreview = "${StrictPricingEngine.formatHours(defaults.minHours)}–${StrictPricingEngine.formatHours(defaults.maxHours)}h est."

    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .testTag("problem_btn_${category.shortTag}"),
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) SpeakeasyCardElevated else SpeakeasyCard,
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) SpeakeasyBrass else SpeakeasyBronze.copy(alpha = 0.7f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                color = if (isSelected) SpeakeasyBrass else SpeakeasyCharcoal,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, if (isSelected) SpeakeasyBrass else SpeakeasyBronze),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = iconForProblem(category),
                        contentDescription = null,
                        tint = if (isSelected) SpeakeasyCharcoal else SpeakeasyBrass,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.displayTitle,
                    fontFamily = PlusJakartaSansFamily,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = if (isSelected) SpeakeasyBrassLight else SpeakeasyTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (category == ProblemCategory.PC_BUILD_INQUIRY) {
                        "Rough cost idea + Maverick Computers referral"
                    } else if (category == ProblemCategory.SOMETHING_ELSE) {
                        "Describe your issue in your own words below"
                    } else {
                        "Typical window: $hrsPreview"
                    },
                    fontFamily = RobotoMonoFamily,
                    fontSize = 12.sp,
                    color = if (isSelected) SpeakeasyTextPrimary else SpeakeasyTextSecondary
                )
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = SpeakeasyBrass,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun CustomProblemEstimatorCard(
    visible: Boolean,
    customInput: String,
    isAnalyzing: Boolean,
    validationError: String?,
    customAnalysis: CustomProblemAnalysis?,
    onInputChanged: (String) -> Unit,
    onAnalyzeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .testTag("custom_problem_card"),
            color = SpeakeasyCardElevated,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.5.dp, SpeakeasyBrass)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.EditNote,
                        contentDescription = null,
                        tint = SpeakeasyBrass
                    )
                    Text(
                        text = stringResource(R.string.custom_problem_label),
                        fontFamily = PlusJakartaSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = SpeakeasyTextPrimary
                    )
                }

                OutlinedTextField(
                    value = customInput,
                    onValueChange = onInputChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 110.dp)
                        .testTag("custom_problem_input"),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.custom_problem_placeholder),
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 14.sp,
                            color = SpeakeasyTextMuted
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = SpeakeasyTextPrimary),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SpeakeasyBrass,
                        unfocusedBorderColor = SpeakeasyBronze,
                        cursorColor = SpeakeasyBrass,
                        focusedContainerColor = SpeakeasyCharcoal,
                        unfocusedContainerColor = SpeakeasyCharcoal
                    ),
                    minLines = 3,
                    maxLines = 6
                )

                if (!validationError.isNullOrBlank()) {
                    Text(
                        text = validationError,
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Button(
                    onClick = onAnalyzeClicked,
                    enabled = !isAnalyzing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                        .testTag("get_custom_estimate_button"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SpeakeasyBrass,
                        contentColor = SpeakeasyCharcoal,
                        disabledContainerColor = SpeakeasyBronze,
                        disabledContentColor = SpeakeasyTextPrimary
                    )
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.5.dp,
                            color = SpeakeasyCharcoal
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(R.string.btn_analyzing),
                            fontFamily = PlusJakartaSansFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.btn_get_estimate),
                            fontFamily = PlusJakartaSansFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                if (customAnalysis != null) {
                    Surface(
                        color = SpeakeasyCharcoal,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, SpeakeasyBronze),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = if (customAnalysis.isOfflineFallback) {
                                    "ROUGH OFFLINE ESTIMATE SUMMARY"
                                } else {
                                    "YOUR PROBLEM SUMMARY"
                                },
                                fontFamily = RobotoMonoFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = SpeakeasyBrass
                            )
                            Text(
                                text = customAnalysis.plainSummary,
                                fontFamily = PlusJakartaSansFamily,
                                fontSize = 14.sp,
                                color = SpeakeasyTextPrimary
                            )
                            Text(
                                text = "Estimated Hours: ${StrictPricingEngine.formatHours(customAnalysis.minHours)}–${StrictPricingEngine.formatHours(customAnalysis.maxHours)} hours (applied to the quote ledger below)",
                                fontFamily = RobotoMonoFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = SpeakeasyBrassLight
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomerAndServiceSelector(
    selectedCustomerType: CustomerType,
    selectedServiceMode: ServiceMode,
    isRecurring: Boolean,
    onCustomerTypeSelected: (CustomerType) -> Unit,
    onServiceModeSelected: (ServiceMode) -> Unit,
    onRecurringToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Customer Type: Home vs Business
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.customer_type_heading),
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = SpeakeasyBrassLight
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SelectionToggleTile(
                    title = stringResource(R.string.customer_home),
                    subtitle = stringResource(R.string.customer_home_desc),
                    icon = Icons.Default.Home,
                    isSelected = selectedCustomerType == CustomerType.HOME,
                    onClick = { onCustomerTypeSelected(CustomerType.HOME) },
                    testTag = "customer_type_home",
                    modifier = Modifier.weight(1f)
                )
                SelectionToggleTile(
                    title = stringResource(R.string.customer_business),
                    subtitle = stringResource(R.string.customer_business_desc),
                    icon = Icons.Default.Business,
                    isSelected = selectedCustomerType == CustomerType.BUSINESS,
                    onClick = { onCustomerTypeSelected(CustomerType.BUSINESS) },
                    testTag = "customer_type_business",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 2. Service Format: Remote ($65/hr) vs On-site visit ($85/hr, 1-hr min)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.service_mode_heading),
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = SpeakeasyBrassLight
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SelectionToggleTile(
                    title = stringResource(R.string.service_remote),
                    subtitle = stringResource(R.string.service_remote_rate),
                    icon = Icons.Default.SupportAgent,
                    isSelected = selectedServiceMode == ServiceMode.REMOTE,
                    onClick = { onServiceModeSelected(ServiceMode.REMOTE) },
                    testTag = "service_mode_remote",
                    modifier = Modifier.weight(1f),
                    useMonoSubtitle = true
                )
                SelectionToggleTile(
                    title = stringResource(R.string.service_onsite),
                    subtitle = stringResource(R.string.service_onsite_rate),
                    icon = Icons.Default.LocationOn,
                    isSelected = selectedServiceMode == ServiceMode.ON_SITE,
                    onClick = { onServiceModeSelected(ServiceMode.ON_SITE) },
                    testTag = "service_mode_onsite",
                    modifier = Modifier.weight(1f),
                    useMonoSubtitle = true
                )
            }
        }

        // 3. Recurring / Ongoing Need Switch
        Surface(
            onClick = { onRecurringToggled(!isRecurring) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 64.dp)
                .testTag("recurring_issue_toggle"),
            color = if (isRecurring) SpeakeasyCardElevated else SpeakeasyCard,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                width = if (isRecurring) 2.dp else 1.dp,
                color = if (isRecurring) SpeakeasyBrass else SpeakeasyBronze
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Repeat,
                    contentDescription = null,
                    tint = SpeakeasyBrass,
                    modifier = Modifier.size(24.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.recurring_toggle_title),
                        fontFamily = PlusJakartaSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = SpeakeasyTextPrimary
                    )
                    Text(
                        text = stringResource(R.string.recurring_toggle_subtitle),
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 13.sp,
                        color = SpeakeasyTextSecondary
                    )
                }
                Switch(
                    checked = isRecurring,
                    onCheckedChange = onRecurringToggled,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = SpeakeasyCharcoal,
                        checkedTrackColor = SpeakeasyBrass,
                        uncheckedThumbColor = SpeakeasyTextSecondary,
                        uncheckedTrackColor = SpeakeasyCharcoal,
                        uncheckedBorderColor = SpeakeasyBronze
                    )
                )
            }
        }
    }
}

@Composable
private fun SelectionToggleTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier,
    useMonoSubtitle: Boolean = false
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .heightIn(min = 92.dp)
            .testTag(testTag),
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) SpeakeasyCardElevated else SpeakeasyCard,
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) SpeakeasyBrass else SpeakeasyBronze
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) SpeakeasyBrass else SpeakeasyTextSecondary,
                    modifier = Modifier.size(22.dp)
                )
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = SpeakeasyBrass,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontFamily = PlusJakartaSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (isSelected) SpeakeasyBrassLight else SpeakeasyTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontFamily = if (useMonoSubtitle) RobotoMonoFamily else PlusJakartaSansFamily,
                fontWeight = if (useMonoSubtitle) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = if (isSelected) SpeakeasyTextPrimary else SpeakeasyTextSecondary
            )
        }
    }
}
