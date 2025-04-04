package com.proteam.aiskincareadvisor.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proteam.aiskincareadvisor.R

@Composable
fun LoginScreen(onBack: () -> Unit, onRegisterClick: () -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val primaryColor = Color(0xFF6A43E8)
    val iconColor = Color(0xFF757575)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = Color(0xFF4A4A4A)
                )
            }
        }

        // App logo
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(vertical = 16.dp)
        )

        // Heading
        Text(
            "Welcome Back",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A4A4A)
        )

        Text(
            "Sign in to continue your skincare journey",
            fontSize = 14.sp,
            color = Color(0xFF757575),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        // Input fields with improved styling
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = null,
                    tint = iconColor
                )
            },
            placeholder = { Text("Email Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Color(0xFFCCCCCC)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = null,
                    tint = iconColor
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible)
                                R.drawable.ic_visibility
                            else
                                R.drawable.ic_visibility_off
                        ),
                        contentDescription = "Toggle password visibility",
                        tint = Color(0xFF757575)
                    )
                }
            },
            placeholder = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Color(0xFFCCCCCC)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        // Remember me and forgot password
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    colors = CheckboxDefaults.colors(checkedColor = primaryColor)
                )
                Text(
                    "Remember me",
                    fontSize = 13.sp,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Text(
                "Forgot Password?",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                modifier = Modifier.clickable { /* Handle forgot password */ }
            )
        }

        // Login button with improved styling
        Button(
            onClick = { /* Handle login */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
        ) {
            Text(
                "LOGIN",
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // OR divider
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.weight(1f),
                color = Color(0xFFCCCCCC),
                thickness = 1.dp
            )
            Text(
                "OR",
                fontSize = 14.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Divider(
                modifier = Modifier.weight(1f),
                color = Color(0xFFCCCCCC),
                thickness = 1.dp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Social login buttons
        Button(
            onClick = { /* Handle Google login */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, Color(0xFFCCCCCC))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Google",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Continue with Google",
                    color = Color(0xFF4A4A4A)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Handle Facebook login */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, Color(0xFFCCCCCC))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Facebook",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Continue with Facebook",
                    color = Color(0xFF4A4A4A)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Register link
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Don't have an account? ",
                fontSize = 14.sp,
                color = Color(0xFF757575)
            )
            Text(
                "Sign Up",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }

        // Footer with help
        TextButton(
            onClick = { /* Show Help */ },
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Help",
                tint = primaryColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "Need Help?",
                color = iconColor,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { /* Show Help */ },
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Help",
                    tint = primaryColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Need Help?",
                    color = iconColor,
                    fontSize = 12.sp
                )
            }

            IconButton(onClick = { /* Toggle Dark Mode */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dark_mode),
                    contentDescription = "Dark Mode",
                    tint = iconColor,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}