package com.proteam.aiskincareadvisor.data.model

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