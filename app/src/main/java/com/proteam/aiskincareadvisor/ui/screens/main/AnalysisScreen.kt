package com.proteam.aiskincareadvisor.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proteam.aiskincareadvisor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen() {
    Scaffold(

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            item { LastScanHeader() }
            item { SkinHealthSummarySection() }
            item { DetailedBreakdownSection() }
            item { ReanalyzeButton() }
            item { ViewRoutineRecommendationsLink() }
            item { TipsAndInsightsSection() }

            // Add some bottom padding for better UX
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// Last Scan Header
@Composable
fun LastScanHeader() {
    Text(
        text = "Last Scan\nJanuary 15, 2025 • 2:30 PM",
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

// Skin Health Summary Section
@Composable
fun SkinHealthSummarySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "SKIN HEALTH SUMMARY",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SummaryItem("Hydration", "Good", Color(0xFF2196F3)) // Blue
            SummaryItem("Oil Level", "Normal", Color(0xFFFFC107)) // Yellow
            SummaryItem("Condition", "Healthy", Color(0xFF4CAF50)) // Green
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String, circleColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(circleColor)
        ) {
            // Placeholder for icon inside the circle
            // You can add an icon here if needed
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// Detailed Breakdown Section
@Composable
fun DetailedBreakdownSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "DETAILED BREAKDOWN",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        BreakdownItem("Skin Type", "Combination")
        BreakdownItem("Concerns", "Mild Redness, Occasional Acne")
    }
}

@Composable
fun BreakdownItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_lock), // Replace with your circle icon
            contentDescription = label,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

// Reanalyze Skin Button
@Composable
fun ReanalyzeButton() {
    Button(
        onClick = { /* Handle reanalyze */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)), // Blue color
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Reanalyze Skin",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

// View Routine Recommendations Link
@Composable
fun ViewRoutineRecommendationsLink() {
    Text(
        text = "View Routine Recommendations",
        fontSize = 16.sp,
        color = Color(0xFF2196F3), // Blue color
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { /* Handle click */ }
    )
}

// Tips & Insights Section
@Composable
fun TipsAndInsightsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "TIPS & INSIGHTS",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        TipItem("Apply sunscreen daily, even on cloudy days")
        TipItem("Drink 8 glasses of water daily for optimal skin hydration")
    }
}

@Composable
fun TipItem(tip: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_lock), // Replace with your tip icon
            contentDescription = "Tip",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = tip,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}