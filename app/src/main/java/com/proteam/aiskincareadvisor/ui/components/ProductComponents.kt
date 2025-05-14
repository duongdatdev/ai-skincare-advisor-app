package com.proteam.aiskincareadvisor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.proteam.aiskincareadvisor.R
import com.proteam.aiskincareadvisor.data.model.Product

@Composable
fun ProductDetailDialog(
    product: Product,
    primaryColor: Color,
    textPrimaryColor: Color,
    textSecondaryColor: Color,
    onDismiss: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 600.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Product Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.imageUrl)
                            .crossfade(true)
                            .error(R.drawable.placeholder)
                            .placeholder(R.drawable.placeholder)
                            .build(),
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Close button
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.7f), CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search), // Use a close icon
                            contentDescription = "Close",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                
                // Product Details
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Product Name
                    Text(
                        text = product.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = textPrimaryColor
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Price
                    Text(
                        text = product.price,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryColor
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Category
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Category: ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = textPrimaryColor
                        )
                        
                        Text(
                            text = product.category,
                            fontSize = 14.sp,
                            color = textSecondaryColor
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Skin Types
                    if (product.skinTypes.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "Suitable for: ",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = textPrimaryColor
                            )
                            
                            Text(
                                text = product.skinTypes.joinToString(", "),
                                fontSize = 14.sp,
                                color = textSecondaryColor
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    // Description
                    Text(
                        text = "Description",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = textPrimaryColor
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = product.description,
                        fontSize = 14.sp,
                        color = textSecondaryColor,
                        lineHeight = 20.sp
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Buy Button
                    Button(
                        onClick = { 
                            if (product.buyLink.isNotEmpty()) {
                                try {
                                    uriHandler.openUri(product.buyLink)
                                } catch (e: Exception) {
                                    // Handle error
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor
                        ),
                        shape = RoundedCornerShape(8.dp),
                        enabled = product.buyLink.isNotEmpty()
                    ) {
                        Text(
                            text = "Buy Now",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
} 