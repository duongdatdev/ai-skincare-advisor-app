// MainScreen.kt
package com.proteam.aiskincareadvisor.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.proteam.aiskincareadvisor.R
import com.proteam.aiskincareadvisor.ui.screens.analysis.SkinAnalysisScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onLogout: () -> Unit) {

    val navController = rememberNavController()
    val screens = listOf(
        BottomNavItem("home", "Home", ImageVector.vectorResource(id = R.drawable.ic_home)),
        BottomNavItem("analysis", "Analyze", ImageVector.vectorResource(id = R.drawable.ic_camera)),
        BottomNavItem("products", "Products", Icons.Default.ShoppingCart),
        BottomNavItem("profile", "Profile", Icons.Default.Person)
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBars = currentRoute != "chat"

    Scaffold(
        topBar = {
            if (showBars) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Lemmie",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        // Chat icon - navigates to chat screen
                        IconButton(onClick = {
                            navController.navigate("chat") {
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = "Chat"
                            )
                        }

                        // Notification icon
                        IconButton(onClick = { /* Handle notifications */ }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications"
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showBars) {
                NavigationBar {
                    val currentDestination =
                        navController.currentBackStackEntryAsState().value?.destination?.route
                    screens.forEach { screen ->
                        NavigationBarItem(
                            selected = currentDestination == screen.route,
                            onClick = {
                                if (currentDestination != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo("home") { inclusive = false }
                                        launchSingleTop = true
                                    }
                                }
                            },
                            icon = { Icon(screen.icon, contentDescription = screen.label) },
                            label = { Text(screen.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("analysis") {
                AnalysisScreen(
                    navController = navController,
                    onNavigateToAnalysis = {
                        navController.navigate("skin_analysis")
                    }
                )
            }
            composable("skin_analysis") {
                SkinAnalysisScreen()
            }
            composable("products") { ProductScreen() }
            composable("profile") { ProfileScreen(navController = navController,onLogout = onLogout) }
            composable("chat") {
                ChatScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable("change_password") {
                ChangePasswordScreen(navController = navController)
            }

            composable("routine") {
                RoutineScreen()
            }
        }
    }
}

data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)

