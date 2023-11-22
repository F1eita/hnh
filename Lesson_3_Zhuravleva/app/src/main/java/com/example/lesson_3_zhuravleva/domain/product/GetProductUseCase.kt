package com.example.lesson_3_zhuravleva.domain.product

import com.example.lesson_3_zhuravleva.data.repository.LessonRepository
import com.example.lesson_3_zhuravleva.data.responsemodel.toProductInfo
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: LessonRepository
) {

    suspend fun execute(id: String): ProductInfo{
        return repository.getProduct(id)
    }
}