package com.example.lesson_3_zhuravleva.data.responsemodel

import com.example.lesson_3_zhuravleva.domain.catalog.Product
import com.google.gson.annotations.SerializedName

data class ResponseProduct(
    @SerializedName("title") val title: String,
    @SerializedName("department") val department: String,
    @SerializedName("price") val price: Int,
    @SerializedName("preview") val preview: String,
    @SerializedName("id") val id: String,
)

fun ResponseProduct.toProduct(): Product {
    return Product(
        title = title,
        department = department,
        price = price,
        preview = preview,
        id = id
    )
}