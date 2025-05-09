package com.proteam.aiskincareadvisor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.proteam.aiskincareadvisor.data.auth.FirebaseAuthHelper
import com.proteam.aiskincareadvisor.ui.screens.SkincareHomeScreen
import com.proteam.aiskincareadvisor.ui.screens.LoginScreen
import com.proteam.aiskincareadvisor.ui.screens.main.MainScreen
import com.proteam.aiskincareadvisor.ui.screens.RegisterScreen
import com.proteam.aiskincareadvisor.ui.screens.main.ChangePasswordScreen
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
    val firebaseAuthHelper = FirebaseAuthHelper()

    NavHost(
        navController = navController,
        startDestination = if (firebaseAuthHelper.isUserAuthenticated()) "main" else "home"
    ) {
        composable("home") {
            SkincareHomeScreen(
                onGetStartedClick = { navController.navigate("register") },
                onLoginClick = { navController.navigate("login") }
            )
        }

        composable("login") {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onRegisterClick = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onLoginClick = { navController.navigate("login") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("main") {
            MainScreen(
                onLogout = {
                    navController.navigate("home") {
                        popUpTo("main") { inclusive = true }
                    }
                }

            )
        }

        // Add the change_password route

    }
}