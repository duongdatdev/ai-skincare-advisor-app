package com.proteam.aiskincareadvisor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.proteam.aiskincareadvisor.ui.screens.SkincareHomeScreen
import com.proteam.aiskincareadvisor.ui.screens.GetStartedScreen
import com.proteam.aiskincareadvisor.ui.screens.LoginScreen
import com.proteam.aiskincareadvisor.ui.theme.AISkincareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AISkincareTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            SkincareHomeScreen(
                onGetStartedClick = { navController.navigate("getStarted") },
                onLoginClick = { navController.navigate("login") }
            )
        }
        composable("getStarted") {
            GetStartedScreen(onBack = { navController.popBackStack() })
        }
        composable("login") {
            LoginScreen(onBack = { navController.popBackStack() })
        }
    }
}