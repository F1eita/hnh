package com.example.lesson_3_zhuravleva.domain.catalog

import com.example.lesson_3_zhuravleva.data.repository.LessonRepository
import com.example.lesson_3_zhuravleva.data.responsemodel.toProduct
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: LessonRepository
) {
    suspend fun execute(): List<Product> {
        return repository.getProductsList().map{it.toProduct()}
    }
}