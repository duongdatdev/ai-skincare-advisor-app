package com.proteam.aiskincareadvisor.ui.screens.main

import androidx.compose.runtime.Composable
import com.proteam.aiskincareadvisor.data.viewmodel.RoutineViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proteam.aiskincareadvisor.data.viewmodel.SkinHistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(viewModel: RoutineViewModel = viewModel()) {
    val routine by viewModel.routineSteps.collectAsState()
    val historyViewModel: SkinHistoryViewModel = viewModel()
    val latestResult by historyViewModel.latestResult.collectAsState()

    LaunchedEffect(latestResult) {
        latestResult?.let {
            if (routine.isEmpty()) {
                viewModel.loadFromAnalysis(it.recommendations)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Routine Hôm Nay", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(routine) { index, step ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = step.isDone,
                            onCheckedChange = { viewModel.toggleStep(index) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = step.step,
                            onValueChange = { viewModel.updateStep(index, it) },
                            modifier = Modifier.weight(1f),
                            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                            maxLines = 4,
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
            }
        }
    }
}