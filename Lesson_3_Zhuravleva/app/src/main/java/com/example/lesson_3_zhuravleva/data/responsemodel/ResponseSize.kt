package com.example.lesson_3_zhuravleva.data.responsemodel

import com.example.lesson_3_zhuravleva.domain.product.Size
import com.google.gson.annotations.SerializedName

data class ResponseSize(
    @SerializedName("value") val value: String,
    @SerializedName("isAvailable") val isAvailable: Boolean
)

fun ResponseSize.toSize(): Size{
    return Size(value = value,
        isAvailable = isAvailable)
}
