// MainScreen.kt
package com.proteam.aiskincareadvisor.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.proteam.aiskincareadvisor.R

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val screens = listOf(
        BottomNavItem("home", "Home", ImageVector.vectorResource(id = R.drawable.ic_home)),
        BottomNavItem("analyze", "Analyze", ImageVector.vectorResource(id = R.drawable.ic_camera)),
        BottomNavItem("products", "Products", Icons.Default.ShoppingCart),
        BottomNavItem("profile", "Profile", Icons.Default.Person)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("analyze") { AnalyzeScreen() }
            composable("products") { ProductScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}

data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)
@Composable
fun HomeScreen() {
    Text("Home Screen (Giao diện Home bạn gửi)")
}

@Composable
fun AnalyzeScreen() {
    Text("Analyze Screen")
}

@Composable
fun ProductScreen() {
    Text("Product Screen (Giao diện Products bạn gửi)")
}

@Composable
fun ProfileScreen() {
    Text("Profile Screen")
}
