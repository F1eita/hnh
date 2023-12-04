package com.example.lesson_3_zhuravleva.domain.catalog

data class Product(
    val id: String,
    val title: String,
    val preview: String,
    val price: Int,
    val department: String
)