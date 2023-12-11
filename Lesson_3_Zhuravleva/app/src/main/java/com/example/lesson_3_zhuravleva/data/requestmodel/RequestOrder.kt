package com.example.lesson_3_zhuravleva.data.requestmodel

import com.google.gson.annotations.SerializedName

data class RequestOrder(
    @SerializedName("House") val house: String,
    @SerializedName("Apartment") val apartment: String,
    @SerializedName("DateDelivery") val dateDelivery: String,
    @SerializedName("Products") val products: List<RequestProduct>,
)