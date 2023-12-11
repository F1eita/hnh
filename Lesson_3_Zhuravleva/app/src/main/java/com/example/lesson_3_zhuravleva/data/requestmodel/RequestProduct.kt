package com.example.lesson_3_zhuravleva.data.requestmodel

import com.google.gson.annotations.SerializedName

data class RequestProduct(
    @SerializedName("ProductId") val productId: String,
    @SerializedName("Size") val size: String,
    @SerializedName("Quantity") val quantity: Int,
)