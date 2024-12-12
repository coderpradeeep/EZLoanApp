package com.example.ezloan.NavGraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ezloan.AboutUsScreen
import com.example.ezloan.Data.MainViewModel
import com.example.ezloan.ScreenElements

@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.AboutUsScreenRoute(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    navigation(
        startDestination = "AboutUs",
        route = "ThreeDotDrawer"
    ) {
        composable(
            route = "AboutUs"
        ) {
            AboutUsScreen(navController)
        }
    }
}