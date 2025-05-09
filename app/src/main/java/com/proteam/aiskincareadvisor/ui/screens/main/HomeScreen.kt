package com.proteam.aiskincareadvisor.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.proteam.aiskincareadvisor.R
import com.proteam.aiskincareadvisor.data.viewmodel.SkinHistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(navController: NavController) {
    val historyViewModel: SkinHistoryViewModel = viewModel()
    val latestResult by historyViewModel.latestResult.collectAsState()

    val primaryColor = Color(0xFF7C3AED)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(bottom = 16.dp)
    ) {
        // Welcome section
        item {
            WelcomeSection(onAnalyzeClick = {
                navController.navigate("skin_analysis")
            })
        }

        // Latest analysis summary (if available)
        item {
            latestResult?.let { result ->
                SkinSummaryCard(result.timestamp, result.skinType, result.overallCondition) {
                    navController.navigate("analysis")
                }
            }
        }

        // Quick actions
        item {
            QuickActionsSection(
                onChatClick = { navController.navigate("chat") },
                onAnalyzeClick = { navController.navigate("skin_analysis") },
                onRoutineClick = { navController.navigate("routine") }
            )
        }

        // Recommended products
        item {
            RecommendedProductsSection(onSeeAllClick = {
                navController.navigate("products")
            })
        }

        // Tips and advice
        item {
            DailyTipsSection()
        }
    }
}

@Composable
fun WelcomeSection(onAnalyzeClick: () -> Unit) {
    val primaryColor = Color(0xFF7C3AED)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(primaryColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Chào ngày mới!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                "Hãy để Lemmie giúp bạn chăm sóc làn da tốt hơn mỗi ngày",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onAnalyzeClick,
                modifier = Modifier.height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    "Phân tích da ngay",
                    color = primaryColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SkinSummaryCard(timestamp: Long, skinType: String, condition: String, onClick: () -> Unit) {
    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        .format(Date(timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Phân tích gần nhất",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    formattedDate,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Loại da:",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        skinType,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Tình trạng:",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        condition,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Xem chi tiết >",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7C3AED),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun QuickActionsSection(
    onChatClick: () -> Unit,
    onAnalyzeClick: () -> Unit,
    onRoutineClick: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Truy cập nhanh",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickActionButton(
                icon = R.drawable.ic_camera,
                title = "Phân tích",
                modifier = Modifier.weight(1f),
                onClick = onAnalyzeClick
            )

            Spacer(modifier = Modifier.width(12.dp))

            QuickActionButton(
                icon = R.drawable.ic_spa,
                title = "Quy trình",
                modifier = Modifier.weight(1f),
                onClick = onRoutineClick
            )

            Spacer(modifier = Modifier.width(12.dp))

            QuickActionButton(
                icon = R.drawable.ic_lock, // Replace with proper chat icon
                title = "Chat AI",
                modifier = Modifier.weight(1f),
                onClick = onChatClick
            )
        }
    }
}

@Composable
fun QuickActionButton(
    icon: Int,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF7C3AED)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF7C3AED)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun RecommendedProductsSection(onSeeAllClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Sản phẩm gợi ý",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            TextButton(onClick = onSeeAllClick) {
                Text(
                    "Xem tất cả",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF7C3AED)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Sample products, replace with actual data from API or repository
        val products = listOf(
            "Kem Dưỡng Ẩm Neutrogena",
            "Sữa Rửa Mặt Gentle Cleanser",
            "Essence Snail Repair",
            "Serum Vitamin C"
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                ProductItem(name = product)
            }
        }
    }
}

@Composable
fun ProductItem(name: String) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(200.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "350.000 ₫",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7C3AED)
                )
            }
        }
    }
}

@Composable
fun DailyTipsSection() {
    val tips = listOf(
        "Uống đủ nước mỗi ngày giúp làn da khỏe mạnh và tươi trẻ",
        "Luôn sử dụng kem chống nắng, kể cả khi ở trong nhà",
        "Đắp mặt nạ 1-2 lần/tuần để cấp ẩm và làm dịu da"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Tips",
                    tint = Color(0xFF7C3AED)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    "Tips chăm sóc da",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = Color.LightGray
            )

            tips.forEach { tip ->
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF7C3AED))
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = tip,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}