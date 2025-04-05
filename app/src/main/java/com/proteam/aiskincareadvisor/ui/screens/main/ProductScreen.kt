package com.proteam.aiskincareadvisor.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

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

// Main Composable for the screen
@Composable
fun ProductScreen() {
    Scaffold(

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Category Tabs
            CategoryTabs()

            // Filter and Sort Row
            FilterSortRow()

            // Product Grid
            ProductGrid()

            // Recommended Section
            RecommendedSection()
        }
    }
}

// Category Tabs (All, Serum, Moisturizer, Sunscreen)
@Composable
fun CategoryTabs() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TabItem("All", isSelected = true)
        TabItem("Serum")
        TabItem("Moisturizer")
        TabItem("Sunscreen")
    }
}

@Composable
fun TabItem(text: String, isSelected: Boolean = false) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) Color.Black else Color.Gray,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { /* Handle tab click */ }
    )
}

// Filter and Sort Row
@Composable
fun FilterSortRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF4CAF50)) // Green background for Filters
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                // Replace with your filter icon
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Filters",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Filters",
                color = Color.White,
                fontSize = 14.sp
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                // Replace with your sort icon
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Sort",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sort",
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

// Product Grid
@Composable
fun ProductGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(2) { index ->
            ProductCard(
                imageRes = if (index == 0) R.drawable.hydrating_moisturizer else R.drawable.vitamin_c_serum, // Replace with your image resources
                name = if (index == 0) "Hydrating Moisturizer" else "Vitamin C Serum",
                price = if (index == 0) "$24.89" else "$29.99"
            )
        }
    }
}

@Composable
fun ProductCard(imageRes: Int, name: String, price: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle product click */ }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = price,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Button(
                onClick = { /* Handle add to cart */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Green button
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .height(32.dp)
            ) {
                Text(
                    text = "Add",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}

// Recommended Section
@Composable
fun RecommendedSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Recommended for you",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(2) { index ->
                ProductCard(
                    imageRes = if (index == 0) R.drawable.hydrating_moisturizer else R.drawable.hydrating_moisturizer, // Replace with your image resources
                    name = if (index == 0) "Daily Sunscreen SPF 50" else "Clay Mask",
                    price = if (index == 0) "$19.99" else "$15.99"
                )
            }
        }
    }
}
