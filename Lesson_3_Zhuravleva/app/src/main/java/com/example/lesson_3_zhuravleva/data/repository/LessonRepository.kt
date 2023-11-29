package com.example.lesson_3_zhuravleva.data.repository

import com.example.lesson_3_zhuravleva.data.ApiLesson
import com.example.lesson_3_zhuravleva.data.db.ProductDao
import com.example.lesson_3_zhuravleva.data.db.toProduct
import com.example.lesson_3_zhuravleva.data.requestmodel.RequestLogin
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseLogin
import com.example.lesson_3_zhuravleva.data.responsemodel.toOrderInfo
import com.example.lesson_3_zhuravleva.data.responsemodel.toProduct
import com.example.lesson_3_zhuravleva.data.responsemodel.toProductInfo
import com.example.lesson_3_zhuravleva.domain.catalog.Product
import com.example.lesson_3_zhuravleva.domain.catalog.toEntity
import com.example.lesson_3_zhuravleva.domain.order.Order
import com.example.lesson_3_zhuravleva.domain.order.CreatedOrderInfo
import com.example.lesson_3_zhuravleva.domain.order.toRequest
import com.example.lesson_3_zhuravleva.domain.product.ProductInfo
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val apiLesson: ApiLesson,
    private val productDao: ProductDao,
) {

    suspend fun login(email: String, password: String): ResponseLogin {
        return apiLesson.login(RequestLogin(email, password)).data
    }

    suspend fun getProductsList(): List<Product> {
        return apiLesson.getProductsList().data.map { it.toProduct() }
    }

    suspend fun getProduct(id: String): ProductInfo {
        return apiLesson.getProduct(id).data.toProductInfo()
    }

    suspend fun getProductsFromDb(): List<Product> {
        return productDao.getProducts().map { it.toProduct() }
    }

    suspend fun addProductToDb(product: Product){
        productDao.addProduct(product.toEntity())
    }

    suspend fun postOrder(order: Order): CreatedOrderInfo {
        return apiLesson.postOrder(order.toRequest()).data.toOrderInfo()
    }
}