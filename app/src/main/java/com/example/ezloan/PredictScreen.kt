package com.example.ezloan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.ezloan.Data.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun PredictScreen(
    navController : NavHostController,
    viewModel: MainViewModel
) {

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TOP_COLOR,
                        BOTTOM_COLOR
                    ),
                    startY = 0f,
                    endY = 1200f
                )
            )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(
                Modifier
                    .weight(1f)
                    .background(Color.Transparent)
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.Transparent)
            ) {

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .padding(PaddingValues(bottom = 32.dp, start = 16.dp, end = 16.dp))
                ) {
                    TextOnly(viewModel)

                    PredictButton(navController, viewModel)
                }

                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(vertical = 8.dp)
                ) {
                    Spacer(Modifier.padding(vertical = 50.dp))
                    //NavButtonsOnly(navController, viewModel)
                }
            }

        }

    }
}

@Composable
fun PredictButton(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val isProMode by viewModel.proMode.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.isPredictScreen = true
    }

    Button(
        shape = CircleShape,
        //elevation = ButtonDefaults.buttonElevation(8.dp),
        colors = ButtonDefaults.buttonColors(BUTTON_COLOR),
        modifier = Modifier
            .size(200.dp)
            .shadow(elevation = 24.dp, shape = CircleShape, spotColor = Color.Red),
        onClick = {
            viewModel.viewModelScope.launch {
                if (isProMode != true) {
                    viewModel.addMode(true)
                }
            }
            viewModel.viewModelScope.launch {
                viewModel.getPrediction(viewModel.apiRequestData)
            }
            viewModel.isChatScreen = true
            navController.navigate(route = "Result_Screen")
        }
    ) {
        Text(
            text = "Predict",
            fontSize = 40.sp,
            letterSpacing = 0.sp,
            maxLines = 1,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier
        )
    }
}

@Composable
fun TextOnly(
    viewModel: MainViewModel
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Great ${viewModel.name} !\nNow Smash the button below to predict your loan approval",
            fontSize = 30.sp,
            letterSpacing = 0.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

    }
}