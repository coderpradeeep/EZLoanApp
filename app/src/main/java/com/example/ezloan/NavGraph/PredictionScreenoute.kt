package com.example.ezloan.NavGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ezloan.AboutUsScreen
import com.example.ezloan.ChatBot.ChatScreen
import com.example.ezloan.Data.MainViewModel
import com.example.ezloan.PredictScreen
import com.example.ezloan.ResultScreen

fun NavGraphBuilder.PredictionScreenRoute(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    navigation(
        startDestination = "Predict_Screen",
        route = "Prediction_Route"
    ) {
        composable(route = "Predict_Screen") {
            PredictScreen(navController, viewModel)
        }
        composable(route = "Result_Screen") {
            ResultScreen(navController, viewModel)
        }
        composable(route = "Chat_Screen") {
            ChatScreen(navController, viewModel)
        }
    }
}