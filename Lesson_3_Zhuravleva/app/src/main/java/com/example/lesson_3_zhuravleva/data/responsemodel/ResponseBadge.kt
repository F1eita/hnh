package com.example.lesson_3_zhuravleva.data.responsemodel

import com.example.lesson_3_zhuravleva.domain.product.Badge
import com.google.gson.annotations.SerializedName

data class ResponseBadge(
    @SerializedName("value") val value: String,
    @SerializedName("color") val color: String
)

fun ResponseBadge.toBadge(): Badge{
    return Badge(value = value,
        color = color)
}
