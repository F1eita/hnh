package com.example.lesson_3_zhuravleva.domain.order

import com.example.lesson_3_zhuravleva.data.repository.LessonRepository
import javax.inject.Inject

class OrderUseCase @Inject constructor(
    private val repository: LessonRepository
) {
    suspend fun execute(order: Order): CreatedOrderInfo {
        return repository.postOrder(order)
    }
}