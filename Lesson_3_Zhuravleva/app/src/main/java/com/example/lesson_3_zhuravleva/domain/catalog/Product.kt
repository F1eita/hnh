package com.example.lesson_3_zhuravleva.domain.catalog

import com.example.lesson_3_zhuravleva.data.db.ProductEntity

data class Product(
    val id: String,
    val title: String,
    val preview: String,
    val price: Int,
    val department: String
)

fun Product.toEntity(): ProductEntity{
    return ProductEntity(id = id,
        title = title,
        preview = preview,
        price = price,
        department = department)
}