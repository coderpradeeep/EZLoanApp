package com.example.ezloan

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ezloan.Data.MainViewModel

val TOPAPPBAR_COLOR = Color(93, 15, 19, 1)
val TOP_COLOR = Color(138,55,41,255)
val BOTTOM_COLOR = Color(17,17,17,255)
val CARD_COLOR = Color(43,34,32,255)
val BUTTON_COLOR = Color(150,55,41,255)
val BOX_COLOR = Color(80,42,37,255)

@Composable
@NonRestartableComposable
fun ScreenElements(
    navController : NavHostController,
    viewModel : MainViewModel
) {
    viewModel.enteredText = ""
    LaunchedEffect(Unit) {
        viewModel.isPredictScreen = false
        viewModel.isResultScreen = false
        viewModel.isChatScreen = false
    }

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
                .background(Color.Transparent)
                .fillMaxSize()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier
                .weight(1f)
                .background(Color.Transparent)
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.Transparent)
            ) {
                TextAndOptions(navController, viewModel)

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TextField(navController, viewModel)

                    Spacer(Modifier.padding(vertical = 30.dp))
                }
            }
        }

    }
}