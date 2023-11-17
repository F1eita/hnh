package com.example.lesson_3_zhuravleva.data.repository

import com.example.lesson_3_zhuravleva.data.ApiLesson
import com.example.lesson_3_zhuravleva.data.requestmodel.RequestLogin
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseLogin
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseProduct
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val apiLesson: ApiLesson,
) {

    suspend fun login(email: String, password: String): ResponseLogin {
        return apiLesson.login(RequestLogin(email, password)).data
    }

    suspend fun getProductsList(): List<ResponseProduct>{
        return apiLesson.getProductsList().data
    }
}