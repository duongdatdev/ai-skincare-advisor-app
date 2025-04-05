package com.proteam.aiskincareadvisor.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
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
import com.proteam.aiskincareadvisor.R

// Data class to represent a chat message
data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
    val productRecommendation: ProductRecommendation? = null
)

// Data class to represent a product recommendation
data class ProductRecommendation(
    val name: String,
    val description: String,
    val imageRes: Int // Replace with your actual image resource
)

// Main Composable for the Chat Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(onBack: () -> Unit = {}) {
    // Sample chat messages
    val messages = listOf(
        ChatMessage(
            text = "Hello! I'm your AI skincare assistant. How can I help you today with your skincare routine?",
            isFromUser = false
        ),
        ChatMessage(
            text = "I need help with my morning skincare routine",
            isFromUser = true
        ),
        ChatMessage(
            text = "Here's a recommended morning routine for you:",
            isFromUser = false,
            productRecommendation = ProductRecommendation(
                name = "Gentle Foam Cleanser",
                description = "Perfect for morning cleanse",
                imageRes = R.drawable.hydrating_moisturizer // Replace with your image resource
            )
        )
    )

    // State for the text input
    var messageText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat With AI Assistant", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            ChatInputField(
                messageText = messageText,
                onMessageTextChange = { messageText = it },
                onSendClick = {
                    // Handle sending the message
                    messageText = ""
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5)) // Light gray background
        ) {
            // Chat Messages
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                reverseLayout = false // Messages start from the top
            ) {
                items(messages) { message ->
                    ChatMessageItem(message)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Quick Reply Buttons
            QuickReplyButtons()
        }
    }
}

// Chat Message Item
@Composable
fun ChatMessageItem(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isFromUser) {
            // AI's profile image
            Image(
                painter = painterResource(id = R.drawable.ai_profile), // Replace with your AI profile image
                contentDescription = "AI Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .background(
                    if (message.isFromUser) Color(0xFF9C27B0) else Color.White, // Purple for user, white for AI
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 16.sp,
                color = if (message.isFromUser) Color.White else Color.Black
            )

            // Product Recommendation (if any)
            message.productRecommendation?.let { product ->
                Spacer(modifier = Modifier.height(8.dp))
                ProductRecommendationItem(product)
            }
        }

        if (message.isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))
            // User's profile image (optional, not shown in screenshot)
        }
    }
}

// Product Recommendation Item
@Composable
fun ProductRecommendationItem(product: ProductRecommendation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.name,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = product.description,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

// Quick Reply Buttons
@Composable
fun QuickReplyButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuickReplyButton("How to treat acne?")
        QuickReplyButton("Best sunscreen?")
        QuickReplyButton("Night")
    }
}

@Composable
fun QuickReplyButton(text: String) {
    Button(
        onClick = { /* Handle quick reply */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE1BEE7)), // Light purple
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

// Chat Input Field
@Composable
fun ChatInputField(
    messageText: String,
    onMessageTextChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = messageText,
            onValueChange = onMessageTextChange,
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFF0F0F0), RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                if (messageText.isEmpty()) {
                    Text(
                        text = "Type your message...",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = onSendClick,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color(0xFF9C27B0)) // Purple send button
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                tint = Color.White
            )
        }
    }
}
