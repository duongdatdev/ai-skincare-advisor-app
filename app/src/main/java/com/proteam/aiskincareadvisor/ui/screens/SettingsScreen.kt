package com.proteam.aiskincareadvisor.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proteam.aiskincareadvisor.data.preferences.ThemeMode
import com.proteam.aiskincareadvisor.data.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    themeViewModel: ThemeViewModel = viewModel(),
    onBack: () -> Unit
) {
    val themeMode by remember { themeViewModel.themeMode }
    val useDynamicColors by remember { themeViewModel.useDynamicColors }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Theme section
            Text(
                text = "Appearance",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Theme Mode
            Text(
                text = "Theme Mode",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = themeMode == ThemeMode.LIGHT,
                    onClick = { themeViewModel.setThemeMode(ThemeMode.LIGHT) },
                    label = { Text("Light") },
                    leadingIcon = {
                        Icon(
                            if (themeMode == ThemeMode.LIGHT) Icons.Filled.LightMode else Icons.Outlined.LightMode,
                            contentDescription = "Light Mode"
                        )
                    }
                )
                
                FilterChip(
                    selected = themeMode == ThemeMode.DARK,
                    onClick = { themeViewModel.setThemeMode(ThemeMode.DARK) },
                    label = { Text("Dark") },
                    leadingIcon = {
                        Icon(
                            if (themeMode == ThemeMode.DARK) Icons.Filled.DarkMode else Icons.Outlined.DarkMode,
                            contentDescription = "Dark Mode"
                        )
                    }
                )
                
                FilterChip(
                    selected = themeMode == ThemeMode.SYSTEM,
                    onClick = { themeViewModel.setThemeMode(ThemeMode.SYSTEM) },
                    label = { Text("System") },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.PhoneAndroid,
                            contentDescription = "System Default"
                        )
                    }
                )
            }
            
            // Dynamic Colors (Material You)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Palette,
                            contentDescription = "Dynamic Colors",
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Text(
                            text = "Use Dynamic Colors (Material You)",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    
                    Switch(
                        checked = useDynamicColors,
                        onCheckedChange = { themeViewModel.setUseDynamicColors(it) }
                    )
                }
            }
        }
    }
} 