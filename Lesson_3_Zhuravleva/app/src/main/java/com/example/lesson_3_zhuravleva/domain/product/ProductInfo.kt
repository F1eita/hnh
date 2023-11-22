package com.example.lesson_3_zhuravleva.domain.product

data class ProductInfo(
    val title: String,
    val department: String,
    val price: Int,
    val badge: List<Badge>,
    val preview: String,
    val images: List<String>,
    val sizes: List<Size>,
    val description: String,
    val details: List<String>,
    val id: String
)