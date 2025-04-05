package com.proteam.aiskincareadvisor.ui.screens.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.proteam.aiskincareadvisor.R


@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Lemmie", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Row {
                Icon(Icons.Default.Notifications, contentDescription = "Notification")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Skin Status
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Your Skin Today", fontWeight = FontWeight.SemiBold)
                    Text("View Details", color = Color.Blue, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusTag("Hydration", "Good", Color(0xFFE0F7FA))
                    StatusTag("Oil Level", "Normal", Color(0xFFFFF3E0))
                    StatusTag("Overall", "Healthy", Color(0xFFE8F5E9))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Analyze Skin Button
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF448AFF))
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_home), contentDescription = "Analyze Skin")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Analyze Skin")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Routine Buttons
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            RoutineButton("Today's Routine", Color(0xFF7E57C2),modifier = Modifier.weight(1f))
            RoutineButton("Today's Routine", Color(0xFF7E57C2),modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Recommended Products
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Recommended Products", fontWeight = FontWeight.SemiBold)
            Text("View All", color = Color.Blue, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ProductCard("Hydrating Serum", "For dry skin", "$24.99")
            ProductCard("Daily Moisturizer", "All skin types", "$29.99")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Today's Routine
        Text("Today's Routine", fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(8.dp))

        RoutineItem("🧼", "Cleanse", "Morning routine", "7:00 AM")
        RoutineItem("🧴", "Apply Sunscreen", "Morning routine", "7:15 AM")

        Spacer(modifier = Modifier.height(60.dp)) // space for bottom nav
    }
}

@Composable
fun StatusTag(title: String, status: String, bgColor: Color) {
    Column(
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(title, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        Text(status, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RoutineButton(text: String, color: Color, modifier: Modifier = Modifier) {
    Button(
        onClick = { },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Icon(Icons.Default.Info, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, color = Color.White)
    }
}

@Composable
fun ProductCard(name: String, type: String, price: String) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(180.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(name, fontWeight = FontWeight.Bold)
            Text(type, fontSize = 12.sp, color = Color.Gray)
            Text(price, fontWeight = FontWeight.Bold, color = Color(0xFF1E88E5))
        }
    }
}

@Composable
fun RoutineItem(icon: String, title: String, subtitle: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(icon)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }
        Text(time, fontSize = 12.sp, color = Color.Gray)
    }
}
