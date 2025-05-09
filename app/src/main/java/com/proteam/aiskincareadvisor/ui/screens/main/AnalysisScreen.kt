package com.proteam.aiskincareadvisor.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proteam.aiskincareadvisor.R
import com.proteam.aiskincareadvisor.data.viewmodel.SkinHistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(navController: NavController, onNavigateToAnalysis: () -> Unit) {
    val viewModel: SkinHistoryViewModel = viewModel()
    val latestResult by viewModel.latestResult.collectAsState()

    Scaffold(

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5)),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { LastScanHeader(latestResult?.timestamp) }
            item {
                SkinHealthSummarySection(
                    latestResult?.hydrationLevel,
                    latestResult?.oilLevel,
                    latestResult?.overallCondition
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ReanalyzeButton(onReanalyze = onNavigateToAnalysis)
                    ViewRoutineRecommendationsButton { navController.navigate("routine") }
                }
            }
            item {
                DetailedBreakdownSection(
                    latestResult?.skinType,
                    latestResult?.concerns ?: emptyList()
                )
            }
            item { TipsAndInsightsSection(latestResult?.tips ?: emptyList()) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun LastScanHeader(timestamp: Long?) {
    val formattedTime = timestamp?.let {
        val sdf = SimpleDateFormat("dd/MM/yyyy • HH:mm", Locale.getDefault())
        sdf.format(Date(it))
    } ?: "Không có dữ liệu"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Lần phân tích gần nhất",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formattedTime,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun SkinHealthSummarySection(
    hydration: String?,
    oil: String?,
    condition: String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EFFF))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "TỔNG QUAN LÀN DA",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF7C3AED) // tím đậm
            )
            Spacer(modifier = Modifier.height(20.dp))

            SummaryLine("Độ ẩm", hydration ?: "Không có dữ liệu", Color(0xFF2196F3))
            SummaryLine("Độ dầu", oil ?: "Không có dữ liệu", Color(0xFFFFC107))
            SummaryLine("Tổng thể", condition ?: "Không có dữ liệu", Color(0xFF4CAF50))
        }
    }
}

@Composable
fun SummaryLine(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$label: ",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.width(80.dp)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = valueColor,
            fontWeight = FontWeight.Medium
        )
    }
}



@Composable
fun DetailedBreakdownSection(skinType: String?, concerns: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "CHI TIẾT PHÂN TÍCH",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7C3AED)
            )
            Spacer(modifier = Modifier.height(16.dp))
            skinType?.let {
                BreakdownItem("Loại da", it, R.drawable.ic_lock) // Update with appropriate icon
            }
            if (concerns.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                BreakdownItem("Vấn đề da", concerns.joinToString(", "), R.drawable.ic_lock) // Update with appropriate icon
            }
        }
    }
}

@Composable
fun BreakdownItem(label: String, value: String, iconResource: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFF0F0F0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = label,
                tint = Color(0xFF7C3AED),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = value, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun TipsAndInsightsSection(tips: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "MẸO & GỢI Ý",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7C3AED)
            )
            Spacer(modifier = Modifier.height(16.dp))

            val displayTips = if (tips.isNotEmpty()) tips else listOf(
                "Luôn dùng kem chống nắng hàng ngày.",
                "Uống đủ 2 lít nước mỗi ngày để giữ ẩm cho da."
            )

            displayTips.forEach {
                TipItem(it)
                if (it != displayTips.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun TipItem(tip: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(Color(0xFF7C3AED))
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = tip,
            fontSize = 14.sp,
            color = Color.Black,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun ReanalyzeButton(onReanalyze: () -> Unit) {
    Button(
        onClick = onReanalyze,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Phân tích lại",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun ViewRoutineRecommendationsButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFF7C3AED)),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_spa),
            contentDescription = null,
            tint = Color(0xFF7C3AED)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Xem gợi ý chăm sóc da",
            fontSize = 16.sp,
            color = Color(0xFF7C3AED)
        )
    }
}
