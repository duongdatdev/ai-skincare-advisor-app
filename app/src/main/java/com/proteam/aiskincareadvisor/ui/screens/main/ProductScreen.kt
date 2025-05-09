package com.proteam.aiskincareadvisor.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.proteam.aiskincareadvisor.R
import kotlinx.coroutines.tasks.await

data class Product(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val skinTypes: List<String> = emptyList(),
    val price: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val buyLink: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen() {
    // State
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf("All") }
    val scrollState = rememberScrollState()

    // Colors
    val primaryColor = Color(0xFF4CAF50)
    val backgroundColor = Color(0xFFF8F8F8)
    val cardColor = Color.White
    val textPrimaryColor = Color(0xFF333333)
    val textSecondaryColor = Color(0xFF757575)

    // Calculate categories from products
    val allCategories = remember(products) {
        listOf("All") + products.map { it.category }.distinct().sorted()
    }

    // Filter products based on selected category
    val filteredProducts = remember(selectedCategory, products) {
        if (selectedCategory == "All") {
            products
        } else {
            products.filter { it.category == selectedCategory }
        }
    }

    // Recommended products (different from selected category)
    val recommendedProducts = remember(selectedCategory, products) {
        if (selectedCategory == "All" || products.isEmpty()) {
            emptyList()
        } else {
            products.filter { it.category != selectedCategory }.take(4)
        }
    }

    // Fetch products from Firestore
    LaunchedEffect(Unit) {
        try {
            val db = FirebaseFirestore.getInstance()
            val productsCollection = db.collection("products")
            val snapshot = productsCollection.get().await()

            val productsList = snapshot.documents.mapNotNull { document ->
                document.toObject(Product::class.java)?.copy(id = document.id)
            }

            products = productsList
            isLoading = false
        } catch (e: Exception) {
            error = e.message
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Skincare Products",
                        fontWeight = FontWeight.Bold,
                        color = textPrimaryColor
                    )
                },
                actions = {
                    IconButton(onClick = { /* Search action */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = textPrimaryColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cardColor
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
        ) {
            when {
                isLoading -> {
                    LoadingState()
                }
                error != null -> {
                    ErrorState(error!!) { isLoading = true; error = null }
                }
                products.isEmpty() -> {
                    EmptyState()
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        // Category Tabs
                        CategoryTabs(
                            categories = allCategories,
                            selectedCategory = selectedCategory,
                            onCategorySelected = { selectedCategory = it },
                            primaryColor = primaryColor
                        )

                        // Filter and Sort Row
                        FilterSortRow(primaryColor)

                        // Main Title
                        Text(
                            text = if (selectedCategory == "All") "All Products" else selectedCategory,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textPrimaryColor,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        )

                        // Product Grid with fixed height
                        if (filteredProducts.isNotEmpty()) {
                            ProductGrid(
                                products = filteredProducts,
                                primaryColor = primaryColor,
                                cardColor = cardColor,
                                textPrimaryColor = textPrimaryColor,
                                textSecondaryColor = textSecondaryColor
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No products in this category",
                                    color = textSecondaryColor
                                )
                            }
                        }

                        // Recommended Section (only show if we have recommendations)
                        if (recommendedProducts.isNotEmpty()) {
                            RecommendedSection(
                                products = recommendedProducts,
                                primaryColor = primaryColor,
                                cardColor = cardColor,
                                textPrimaryColor = textPrimaryColor,
                                textSecondaryColor = textSecondaryColor
                            )
                        }

                        // Bottom space
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = Color(0xFF4CAF50)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Loading products...",
                color = Color(0xFF757575),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ErrorState(error: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Error",
                tint = Color.Red,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Something went wrong",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                fontSize = 14.sp,
                color = Color(0xFF757575),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                ),
                modifier = Modifier
                    .width(120.dp)
                    .height(48.dp)
            ) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Empty",
                tint = Color(0xFF757575),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No products found",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "We couldn't find any products matching your criteria",
                fontSize = 14.sp,
                color = Color(0xFF757575),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CategoryTabs(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    primaryColor: Color
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory

            Card(
                modifier = Modifier
                    .height(36.dp)
                    .clickable { onCategorySelected(category) },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) primaryColor else Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (isSelected) 4.dp else 1.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun FilterSortRow(primaryColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElevatedButton(
            onClick = { /* Handle filter action */ },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = primaryColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Filters",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Filters",
                fontSize = 14.sp
            )
        }

        ElevatedButton(
            onClick = { /* Handle sort action */ },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF757575)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Sort",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sort",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ProductGrid(
    products: List<Product>,
    primaryColor: Color,
    cardColor: Color,
    textPrimaryColor: Color,
    textSecondaryColor: Color
) {
    // Calculate a reasonable height based on number of products
    val gridHeight = when {
        products.size <= 2 -> 230.dp  // Just one row
        products.size <= 4 -> 460.dp  // Two rows
        else -> 600.dp                // More rows with scrolling
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(gridHeight)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = true// Disable scrolling for main grid
    ) {
        items(products.size) { index ->
            ProductCard(
                product = products[index],
                primaryColor = primaryColor,
                cardColor = cardColor,
                textPrimaryColor = textPrimaryColor,
                textSecondaryColor = textSecondaryColor
            )
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    primaryColor: Color,
    cardColor: Color,
    textPrimaryColor: Color,
    textSecondaryColor: Color
) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle product details */ },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .build(),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            // Favorite button
            IconButton(
                onClick = { isFavorite = !isFavorite },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(32.dp)
                    .background(Color.White.copy(alpha = 0.7f), CircleShape)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textPrimaryColor
            )

            if (product.skinTypes.isNotEmpty()) {
                Text(
                    text = product.skinTypes.joinToString(", "),
                    fontSize = 12.sp,
                    color = textSecondaryColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textPrimaryColor
                )

                Button(
                    onClick = { /* Buy action */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    ),
                    modifier = Modifier.height(30.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Buy",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendedSection(
    products: List<Product>,
    primaryColor: Color,
    cardColor: Color,
    textPrimaryColor: Color,
    textSecondaryColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Section header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recommended For You",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = textPrimaryColor
            )

            TextButton(onClick = { /* View all */ }) {
                Text(
                    text = "View All",
                    fontSize = 14.sp,
                    color = primaryColor
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fixed height grid with 2 items per row
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp), // Fixed height for 1 row
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            userScrollEnabled = false // Disable scrolling for recommended section
        ) {
            items(products.size.coerceAtMost(2)) { index -> // Limit to 2 items
                ProductCard(
                    product = products[index],
                    primaryColor = primaryColor,
                    cardColor = cardColor,
                    textPrimaryColor = textPrimaryColor,
                    textSecondaryColor = textSecondaryColor
                )
            }
        }
    }
}