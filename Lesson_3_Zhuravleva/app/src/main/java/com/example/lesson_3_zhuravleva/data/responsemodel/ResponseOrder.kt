package com.example.lesson_3_zhuravleva.data.responsemodel

import com.example.lesson_3_zhuravleva.domain.order.CreatedOrderInfo
import com.google.gson.annotations.SerializedName

data class ResponseOrder(
    @SerializedName("number") val number: Int,
    @SerializedName("createdDelivery") val createdDelivery: String,
)

fun ResponseOrder.toOrderInfo(): CreatedOrderInfo{
    return CreatedOrderInfo(number = number,
        createdDelivery = createdDelivery)
}