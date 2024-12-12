package com.example.ezloan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ezloan.Data.MainViewModel

@Composable
fun ResultScreen(
    navController : NavHostController,
    viewModel: MainViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.isResultScreen = true
        viewModel.isChatScreen = false
    }

    val isLoading = viewModel.predicting.observeAsState()
    val response = viewModel.prediction.observeAsState()
    val dialogBoxState = viewModel.dialogBoxState.observeAsState()

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
                    .weight(1f)
            ) {

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (response.value != null) {
                        ResultMessage(viewModel)
                    }
                }

                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .padding(vertical = 8.dp)
                ) {
                    Spacer(Modifier.padding(vertical = 50.dp))
                }
            }
        }

        if (isLoading.value == 1) {
            LoadingAnimation(viewModel)
        }
        AnimatedVisibility(
            visible = if(dialogBoxState.value == true) true else false,
            enter = fadeIn(animationSpec = tween(200))
                    + expandIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
                    + shrinkOut(animationSpec = tween(200)),
        ) {
            if (isLoading.value == 2) {
                DialogBox(navController, viewModel)
            }
        }

    }
}

@Composable
fun ResultMessage(
    viewModel: MainViewModel
) {
    var response = viewModel.prediction.observeAsState()

    if(response.value!! >= 0.8f) {
        Text(
            text = "Congrats ${viewModel.name}!\nThere is higher chance of you getting the desired loan amount.\n${response.value}",
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
            letterSpacing = 0.sp,
            lineHeight = 25.sp,
            modifier = Modifier
        )
    } else {
        Text(
            text = "Sorry to say ${viewModel.name},\nYou will have to improve your profile in order to get desired amount.\n${response.value}",
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
            letterSpacing = 0.sp,
            lineHeight = 25.sp,
            modifier = Modifier
        )
    }
}