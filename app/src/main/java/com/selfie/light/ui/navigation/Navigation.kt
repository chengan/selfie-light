package com.selfie.light.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.selfie.light.ui.main.MainScreen
import com.selfie.light.ui.splash.SplashScreen
import com.selfie.light.ui.guide.GuideScreen
import com.selfie.light.ui.guide.GuideViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Navigation(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val isFirstTimeUser by viewModel.isFirstTimeUser.collectAsState(initial = true)

    NavHost(
        navController = navController,
        startDestination = if (isFirstTimeUser) Screen.Guide.route else Screen.Splash.route
    ) {
        composable(Screen.Guide.route) {
            GuideScreen(
                onNavigate = { event ->
                    when (event) {
                        is UiEvent.NavigateToMain -> {
                            navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.Guide.route) { inclusive = true }
                            }
                        }
                        else -> Unit
                    }
                }
            )
        }
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigate = { event ->
                    when (event) {
                        is UiEvent.NavigateToMain -> {
                            navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                        else -> Unit
                    }
                }
            )
        }
        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}

sealed class Screen(val route: String) {
    object Guide : Screen("guide")
    object Splash : Screen("splash")
    object Main : Screen("main")
} 