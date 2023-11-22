package com.example.lesson_3_zhuravleva.data.repository

import com.example.lesson_3_zhuravleva.data.ApiLesson
import com.example.lesson_3_zhuravleva.data.db.ProductDao
import com.example.lesson_3_zhuravleva.data.db.ProductEntity
import com.example.lesson_3_zhuravleva.data.db.toProduct
import com.example.lesson_3_zhuravleva.data.requestmodel.RequestLogin
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseLogin
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseProduct
import com.example.lesson_3_zhuravleva.data.responsemodel.toProduct
import com.example.lesson_3_zhuravleva.data.responsemodel.toProductInfo
import com.example.lesson_3_zhuravleva.domain.catalog.Product
import com.example.lesson_3_zhuravleva.domain.catalog.toEntity
import com.example.lesson_3_zhuravleva.domain.product.ProductInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
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
}