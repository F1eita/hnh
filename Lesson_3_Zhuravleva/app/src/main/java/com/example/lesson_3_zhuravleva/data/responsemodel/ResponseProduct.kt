package com.example.lesson_3_zhuravleva.data.responsemodel

import com.example.lesson_3_zhuravleva.domain.catalog.Product
import com.example.lesson_3_zhuravleva.domain.product.ProductInfo
import com.google.gson.annotations.SerializedName

data class ResponseProduct(
    @SerializedName("title") val title: String,
    @SerializedName("department") val department: String,
    @SerializedName("price") val price: Int,
    @SerializedName("badge") val badge: List<ResponseBadge>,
    @SerializedName("preview") val preview: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("sizes") val sizes: List<ResponseSize>,
    @SerializedName("description") val description: String,
    @SerializedName("details") val details: List<String>,
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

fun ResponseProduct.toProductInfo(): ProductInfo {
    return ProductInfo(
        title = title,
        department = department,
        price = price,
        preview = preview,
        badge = badge.map{it.toBadge()},
        images = images,
        sizes = sizes.map{it.toSize()},
        description = description,
        details = details,
        id = id
    )
}


