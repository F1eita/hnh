package com.example.lesson_3_zhuravleva.domain.order

import com.example.lesson_3_zhuravleva.data.requestmodel.RequestOrder

data class Order(
    val house: String,
    val apartment: String,
    val dateDelivery: String,
    val products: List<OrderProduct>,
)

fun Order.toRequest(): RequestOrder{
    return RequestOrder(house = house,
        apartment = apartment,
        dateDelivery = dateDelivery,
        products = products.map{it.toRequest()})
}
