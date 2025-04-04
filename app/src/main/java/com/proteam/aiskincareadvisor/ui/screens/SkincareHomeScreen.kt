package com.proteam.aiskincareadvisor.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.proteam.aiskincareadvisor.R

@Composable
fun SkincareHomeScreen(
    onGetStartedClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lemmie",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
            )

            Text(
                text = "Your AI-powered skincare companion!",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )

            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Skincare illustration",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 24.dp)
            )

            Column(
                modifier = Modifier.padding(vertical = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "✔ AI-Powered Analysis", style = MaterialTheme.typography.bodyLarge)
                Text(text = "✔ Personalized Routine", style = MaterialTheme.typography.bodyLarge)
                Text(text = "✔ Track Your Progress", style = MaterialTheme.typography.bodyLarge)
            }

            Button(
                onClick = { onGetStartedClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Get Started")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Already have an account? Sign in",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Blue),
                modifier = Modifier.clickable { onSignInClick() }
            )
        }
    }
}
