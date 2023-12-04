package com.example.lesson_3_zhuravleva.domain.login

import com.example.lesson_3_zhuravleva.data.repository.LessonRepository
import com.example.lesson_3_zhuravleva.data.repository.PreferenceStorage
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseLogin
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LessonRepository,
    private val preferenceStorage: PreferenceStorage,
) {
    suspend fun execute(email: String, password: String): ResponseLogin {
        val loginData = repository.login(email, password)
        preferenceStorage.userToken = loginData.accessToken
        return loginData
    }
}
