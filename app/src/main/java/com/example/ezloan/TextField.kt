package com.example.ezloan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ezloan.Data.MainViewModel
import com.example.ezloan.Data.OptionProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    navController : NavHostController,
    viewModel: MainViewModel
) {

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .navigationBarsPadding()
            .padding(top = 8.dp, bottom = 40.dp)
    ) {
        // Text Field
        if(viewModel.page != 1 && viewModel.page != 3 && viewModel.page != 4) {
            OutlinedTextField(
                shape = CircleShape,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color.LightGray
                ),
                leadingIcon = {
                    Icon(
                        imageVector = if(viewModel.page == 0) {
                            Icons.Outlined.Person
                        } else Icons.Outlined.Edit,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                },
                label = {
                    Text(
                        text = if (viewModel.page == 0) {
                            "First Name"
                        } else "Enter Specific No.",
                        color = Color.LightGray
                    )
                },
                value = viewModel.enteredText,
                onValueChange = {
                    if(viewModel.page == 0) {
                        viewModel.name = it
                    }
                    viewModel.enteredText = it
                    var thisValue = it
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
                },
                maxLines = 1,
                textStyle = TextStyle(Color.White),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = if (viewModel.page == 0) {
                        KeyboardType.Text
                    } else KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = null
                ),
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 4.dp)
            )
        }

    }
}