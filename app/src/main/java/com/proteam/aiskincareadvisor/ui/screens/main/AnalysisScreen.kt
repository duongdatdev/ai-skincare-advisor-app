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

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
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
                DetailedBreakdownSection(
                    latestResult?.skinType,
                    latestResult?.concerns ?: emptyList()
                )
            }
            item { ReanalyzeButton(onReanalyze = onNavigateToAnalysis) }
            item { ViewRoutineRecommendationsLink { navController.navigate("routine") } }
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

    Text(
        text = "Lần phân tích gần nhất\n$formattedTime",
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun SkinHealthSummarySection(hydration: String?, oil: String?, condition: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "TỔNG QUAN LÀN DA",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SummaryItem("Độ ẩm", hydration ?: "-", Color(0xFF2196F3))
            SummaryItem("Độ dầu", oil ?: "-", Color(0xFFFFC107))
            SummaryItem("Tổng thể", condition ?: "-", Color(0xFF4CAF50))
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
        ) {}
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun DetailedBreakdownSection(skinType: String?, concerns: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "CHI TIẾT PHÂN TÍCH",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        skinType?.let {
            BreakdownItem("Loại da", it)
        }
        if (concerns.isNotEmpty()) {
            BreakdownItem("Vấn đề da", concerns.joinToString(", "))
        }
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
            painter = painterResource(id = R.drawable.ic_lock),
            contentDescription = label,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = value, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ReanalyzeButton(onReanalyze: () -> Unit) {
    Button(
        onClick = onReanalyze,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Phân tích lại",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun ViewRoutineRecommendationsLink(onClick: () -> Unit) {
    Text(
        text = "Xem gợi ý chăm sóc da",
        fontSize = 16.sp,
        color = Color(0xFF2196F3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }
    )
}


@Composable
fun TipsAndInsightsSection(tips: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "MẸO & GỢI Ý",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (tips.isNotEmpty()) {
            tips.forEach { TipItem(it) }
        } else {
            TipItem("Luôn dùng kem chống nắng hàng ngày.")
            TipItem("Uống đủ 2 lít nước mỗi ngày để giữ ẩm cho da.")
        }
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
            painter = painterResource(id = R.drawable.ic_lock),
            contentDescription = "Tip",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = tip, fontSize = 14.sp, color = Color.Black)
    }
}
