package com.example.lesson_3_zhuravleva.domain.order

import com.example.lesson_3_zhuravleva.data.requestmodel.RequestProduct


data class OrderProduct (
    val productId: String,
    val size: String,
    val quantity: Int,
)

fun OrderProduct.toRequest(): RequestProduct{
    return RequestProduct(productId = productId,
        size = size,
        quantity = quantity)
}