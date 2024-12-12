package com.example.ezloan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ezloan.Data.MainViewModel
import com.example.ezloan.Data.OptionProvider
import com.example.ezloan.Data.TextsDataProvider

data class ToggleableInfo (
    val isSelected : Boolean
)

@Composable
fun TextAndOptions(
    navController : NavHostController,
    viewModel: MainViewModel
) {
    val isProMode by viewModel.proMode.observeAsState()

    var selectedRadioButton = remember {
        mutableStateListOf (
            ToggleableInfo(false),
            ToggleableInfo(false),
            ToggleableInfo(false)
        )
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Transparent)
    ) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            isProMode?.let {
                if (it == true) {
                    AnimatedVisibility(
                        visible = (viewModel.page == 0),
                        enter = fadeIn(animationSpec = tween(300)) + slideInVertically(
                            initialOffsetY = { it }
                        ),
                        exit = fadeOut(animationSpec = tween(200)) + slideOutVertically(
                            targetOffsetY = { it }
                        )
                    ) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = BOX_COLOR),
                            onClick = {
                                viewModel.firstChat = false
                                viewModel.isUser = true
                                navController.navigate("Chat_Screen")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .shadow(elevation = 24.dp, spotColor = Color.Black)
                        ) {
                            Text(
                                text = "Chat With AI",
                                fontSize = 30.sp,
                                letterSpacing = 0.sp,
                                maxLines = 1,
                                fontWeight = FontWeight.W500,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(PaddingValues(horizontal = 24.dp, vertical = 12.dp))
                            )
                        }
                    }
                }
            }

            Text(
                text = if(viewModel.page == 1) {
                    "Hey ${viewModel.name},\n${TextsDataProvider.text.get(viewModel.page).message}"
                } else TextsDataProvider.text.get(viewModel.page).message,
                fontSize = if (viewModel.page == 0) {
                    40.sp
                } else 30.sp,
                letterSpacing = 0.sp,
                lineHeight = if (viewModel.page == 0) {
                    50.sp
                } else 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            )

        }
        if(viewModel.page > 1) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Absolute.Left,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        .clickable {
                            selectedRadioButton[0] = ToggleableInfo(true)
                            selectedRadioButton[1] = ToggleableInfo(false)
                            selectedRadioButton[2] = ToggleableInfo(false)
                            val thisValue = OptionProvider.options[viewModel.page].option1.value.toString()
                            when(viewModel.page) {
                                2 -> viewModel.apiRequestData.no_of_dependents = thisValue.toInt()
                                3 -> viewModel.apiRequestData.education = thisValue.toInt()
                                4 -> viewModel.apiRequestData.self_employed = thisValue.toInt()
                                5 -> viewModel.apiRequestData.income_annum = thisValue.toInt()
                                6 -> viewModel.apiRequestData.loan_amount = thisValue.toInt()
                                7 -> viewModel.apiRequestData.loan_term = thisValue.toInt()
                                8 -> viewModel.apiRequestData.cibil_score = thisValue.toInt()
                                9 -> viewModel.apiRequestData.residential_assets_value = thisValue.toInt()
                                10 -> viewModel.apiRequestData.commercial_assets_value = thisValue.toInt()
                                11 -> viewModel.apiRequestData.luxury_assets_value = thisValue.toInt()
                                12 -> viewModel.apiRequestData.bank_asset_value = thisValue.toInt()
                                else -> viewModel.dummy = thisValue
                            }
                        }
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.White,
                            unselectedColor = Color.Gray
                        ),
                        enabled = true,
                        selected = selectedRadioButton[0].isSelected,
                        onClick = {
                            selectedRadioButton[0] = ToggleableInfo(true)
                            selectedRadioButton[1] = ToggleableInfo(false)
                            selectedRadioButton[2] = ToggleableInfo(false)
                            val thisValue = OptionProvider.options[viewModel.page].option1.value.toString()
                            when(viewModel.page) {
                                2 -> viewModel.apiRequestData.no_of_dependents = thisValue.toInt()
                                3 -> viewModel.apiRequestData.education = thisValue.toInt()
                                4 -> viewModel.apiRequestData.self_employed = thisValue.toInt()
                                5 -> viewModel.apiRequestData.income_annum = thisValue.toInt()
                                6 -> viewModel.apiRequestData.loan_amount = thisValue.toInt()
                                7 -> viewModel.apiRequestData.loan_term = thisValue.toInt()
                                8 -> viewModel.apiRequestData.cibil_score = thisValue.toInt()
                                9 -> viewModel.apiRequestData.residential_assets_value = thisValue.toInt()
                                10 -> viewModel.apiRequestData.commercial_assets_value = thisValue.toInt()
                                11 -> viewModel.apiRequestData.luxury_assets_value = thisValue.toInt()
                                12 -> viewModel.apiRequestData.bank_asset_value = thisValue.toInt()
                                else -> viewModel.dummy = thisValue
                            }
                        }
                    )
                    Text(
                        text = OptionProvider.options.get(viewModel.page).option1.option,
                        fontSize = 25.sp,
                        letterSpacing = 0.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.White,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Absolute.Left,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        .clickable {
                            selectedRadioButton[0] = ToggleableInfo(false)
                            selectedRadioButton[1] = ToggleableInfo(true)
                            selectedRadioButton[2] = ToggleableInfo(false)
                            val thisValue = OptionProvider.options[viewModel.page].option2.value.toString()
                            when(viewModel.page) {
                                2 -> viewModel.apiRequestData.no_of_dependents = thisValue.toInt()
                                3 -> viewModel.apiRequestData.education = thisValue.toInt()
                                4 -> viewModel.apiRequestData.self_employed = thisValue.toInt()
                                5 -> viewModel.apiRequestData.income_annum = thisValue.toInt()
                                6 -> viewModel.apiRequestData.loan_amount = thisValue.toInt()
                                7 -> viewModel.apiRequestData.loan_term = thisValue.toInt()
                                8 -> viewModel.apiRequestData.cibil_score = thisValue.toInt()
                                9 -> viewModel.apiRequestData.residential_assets_value = thisValue.toInt()
                                10 -> viewModel.apiRequestData.commercial_assets_value = thisValue.toInt()
                                11 -> viewModel.apiRequestData.luxury_assets_value = thisValue.toInt()
                                12 -> viewModel.apiRequestData.bank_asset_value = thisValue.toInt()
                                else -> viewModel.dummy = thisValue
                            }
                        }
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.White,
                            unselectedColor = Color.Gray
                        ),
                        enabled = true,
                        selected = selectedRadioButton[1].isSelected,
                        onClick = {
                            selectedRadioButton[0] = ToggleableInfo(false)
                            selectedRadioButton[1] = ToggleableInfo(true)
                            selectedRadioButton[2] = ToggleableInfo(false)
                            val thisValue = OptionProvider.options[viewModel.page].option2.value.toString()
                            when(viewModel.page) {
                                2 -> viewModel.apiRequestData.no_of_dependents = thisValue.toInt()
                                3 -> viewModel.apiRequestData.education = thisValue.toInt()
                                4 -> viewModel.apiRequestData.self_employed = thisValue.toInt()
                                5 -> viewModel.apiRequestData.income_annum = thisValue.toInt()
                                6 -> viewModel.apiRequestData.loan_amount = thisValue.toInt()
                                7 -> viewModel.apiRequestData.loan_term = thisValue.toInt()
                                8 -> viewModel.apiRequestData.cibil_score = thisValue.toInt()
                                9 -> viewModel.apiRequestData.residential_assets_value = thisValue.toInt()
                                10 -> viewModel.apiRequestData.commercial_assets_value = thisValue.toInt()
                                11 -> viewModel.apiRequestData.luxury_assets_value = thisValue.toInt()
                                12 -> viewModel.apiRequestData.bank_asset_value = thisValue.toInt()
                                else -> viewModel.dummy = thisValue
                            }
                        }
                    )
                    Text(
                        text = OptionProvider.options.get(viewModel.page).option2.option,
                        fontSize = 25.sp,
                        letterSpacing = 0.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.White,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Absolute.Left,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        .clickable {
                            selectedRadioButton[0] = ToggleableInfo(false)
                            selectedRadioButton[1] = ToggleableInfo(false)
                            selectedRadioButton[2] = ToggleableInfo(true)
                            val thisValue = OptionProvider.options[viewModel.page].option3.value.toString()
                            when(viewModel.page) {
                                2 -> viewModel.apiRequestData.no_of_dependents = thisValue.toInt()
                                3 -> viewModel.apiRequestData.education = thisValue.toInt()
                                4 -> viewModel.apiRequestData.self_employed = thisValue.toInt()
                                5 -> viewModel.apiRequestData.income_annum = thisValue.toInt()
                                6 -> viewModel.apiRequestData.loan_amount = thisValue.toInt()
                                7 -> viewModel.apiRequestData.loan_term = thisValue.toInt()
                                8 -> viewModel.apiRequestData.cibil_score = thisValue.toInt()
                                9 -> viewModel.apiRequestData.residential_assets_value = thisValue.toInt()
                                10 -> viewModel.apiRequestData.commercial_assets_value = thisValue.toInt()
                                11 -> viewModel.apiRequestData.luxury_assets_value = thisValue.toInt()
                                12 -> viewModel.apiRequestData.bank_asset_value = thisValue.toInt()
                                else -> viewModel.dummy = thisValue
                            }
                        }
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.White,
                            unselectedColor = Color.Gray
                        ),
                        enabled = true,
                        selected = selectedRadioButton[2].isSelected,
                        onClick = {
                            selectedRadioButton[0] = ToggleableInfo(false)
                            selectedRadioButton[1] = ToggleableInfo(false)
                            selectedRadioButton[2] = ToggleableInfo(true)
                            val thisValue = OptionProvider.options[viewModel.page].option3.value.toString()
                            when(viewModel.page) {
                                2 -> viewModel.apiRequestData.no_of_dependents = thisValue.toInt()
                                3 -> viewModel.apiRequestData.education = thisValue.toInt()
                                4 -> viewModel.apiRequestData.self_employed = thisValue.toInt()
                                5 -> viewModel.apiRequestData.income_annum = thisValue.toInt()
                                6 -> viewModel.apiRequestData.loan_amount = thisValue.toInt()
                                7 -> viewModel.apiRequestData.loan_term = thisValue.toInt()
                                8 -> viewModel.apiRequestData.cibil_score = thisValue.toInt()
                                9 -> viewModel.apiRequestData.residential_assets_value = thisValue.toInt()
                                10 -> viewModel.apiRequestData.commercial_assets_value = thisValue.toInt()
                                11 -> viewModel.apiRequestData.luxury_assets_value = thisValue.toInt()
                                12 -> viewModel.apiRequestData.bank_asset_value = thisValue.toInt()
                                else -> viewModel.dummy = thisValue
                            }
                        }
                    )
                    Text(
                        text = OptionProvider.options.get(viewModel.page).option3.option,
                        fontSize = 25.sp,
                        letterSpacing = 0.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.White
                    )
                }
            }
        }
    }
}