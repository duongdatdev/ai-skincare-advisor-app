package com.proteam.aiskincareadvisor.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.border
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.proteam.aiskincareadvisor.R
import com.proteam.aiskincareadvisor.data.auth.FirebaseAuthHelper

@Composable
fun ProfileScreen( navController: NavController,
                   authHelper: FirebaseAuthHelper = remember { FirebaseAuthHelper() }) {
    val primaryColor = Color(0xFF6A43E8) // Using the same purple as in LoginScreen

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        // Profile Header with Cover Image & Profile Image
        item {
            ProfileHeaderWithCover(primaryColor)
        }

        // Main Profile Sections
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                // Skin Health Summary Cards
                SkinHealthSummary()

                Spacer(modifier = Modifier.height(16.dp))

                // Menu Items
                ProfileMenuSection(primaryColor)

                Spacer(modifier = Modifier.height(16.dp))

                // Account Section
                AccountSection(primaryColor)

                Spacer(modifier = Modifier.height(24.dp))

                // Log Out Button
                Button(
                    onClick = {
                        // Use the helper method to sign out
                        authHelper.signOut()

                        // Navigate to login screen
                        navController.navigate("login") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Logout",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "LOG OUT",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ProfileHeaderWithCover(primaryColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        // Cover image/background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(primaryColor)
        )

        // Profile image
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.hydrating_moisturizer),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White, CircleShape)
                    .border(3.dp, Color.White, CircleShape)
            )

            Text(
                text = "Sarah Johnson",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "sarah.johnson@email.com",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Edit Profile Button
        IconButton(
            onClick = { /* Edit profile action */ },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(36.dp)
                .background(Color.White.copy(alpha = 0.8f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile",
                tint = primaryColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SkinHealthSummary() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "SKIN HEALTH",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                SkinMetricItem("Hydration", "75%", Color(0xFF2196F3))
                SkinMetricItem("Oil Level", "Normal", Color(0xFFFFC107))
                SkinMetricItem("Overall", "Good", Color(0xFF4CAF50))
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { /* View full skin analysis */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF2196F3)
                )
            ) {
                Text("View Full Analysis", fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun SkinMetricItem(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            progress = 0.75f, // This would be dynamic based on the value
            modifier = Modifier.size(50.dp),
            color = color,
            strokeWidth = 4.dp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ProfileMenuSection(primaryColor: Color) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "MY SKINCARE",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProfileMenuItem(
                icon = Icons.Outlined.DateRange,
                title = "My Routines",
                subtitle = "View your daily skincare routines",
                primaryColor = primaryColor
            )

            ProfileMenuItem(
                icon = Icons.Outlined.FavoriteBorder,
                title = "My Favorites",
                subtitle = "Products you've saved",
                primaryColor = primaryColor
            )

            ProfileMenuItem(
                icon = Icons.Outlined.Info,
                title = "Order History",
                subtitle = "View your past purchases",
                primaryColor = primaryColor
            )

            ProfileMenuItem(
                icon = Icons.Outlined.ShoppingCart,
                title = "Current Orders",
                subtitle = "Track your deliveries",
                primaryColor = primaryColor,
                showDivider = false
            )
        }
    }
}

@Composable
fun AccountSection(primaryColor: Color) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "ACCOUNT SETTINGS",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProfileMenuItem(
                icon = Icons.Outlined.Person,
                title = "Personal Information",
                subtitle = "Manage your account details",
                primaryColor = primaryColor
            )

            ProfileMenuItem(
                icon = Icons.Outlined.Lock,
                title = "Security",
                subtitle = "Password & privacy",
                primaryColor = primaryColor
            )

            ProfileMenuItem(
                icon = Icons.Outlined.Notifications,
                title = "Notifications",
                subtitle = "Customize notification preferences",
                primaryColor = primaryColor
            )

            ProfileItemWithSwitch(
                icon = ImageVector.vectorResource(id = R.drawable.ic_dark_mode),
                title = "Dark Mode",
                isChecked = false,
                onCheckedChange = { /* Handle dark mode toggle */ },
                primaryColor = primaryColor,
                showDivider = false
            )
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    primaryColor: Color,
    showDivider: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Handle click */ }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = primaryColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }

        if (showDivider) {
            Divider(
                modifier = Modifier.padding(start = 40.dp),
                thickness = 0.5.dp,
                color = Color(0xFFEEEEEE)
            )
        }
    }
}

@Composable
fun ProfileItemWithSwitch(
    icon: ImageVector,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    primaryColor: Color,
    showDivider: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = primaryColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = primaryColor,
                    checkedTrackColor = primaryColor.copy(alpha = 0.5f)
                )
            )
        }

        if (showDivider) {
            Divider(
                modifier = Modifier.padding(start = 40.dp),
                thickness = 0.5.dp,
                color = Color(0xFFEEEEEE)
            )
        }
    }
}