package com.example.lesson_3_zhuravleva.presentation.ui.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseLogin
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseStates
import com.example.lesson_3_zhuravleva.domain.login.LoginUseCase
import javax.inject.Inject
import kotlinx.coroutines.launch

class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _signInLiveData = MutableLiveData<ResponseStates<ResponseLogin>>()
    val signInLiveData: LiveData<ResponseStates<ResponseLogin>> = _signInLiveData

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _signInLiveData.value = ResponseStates.Loading()
            try {
                _signInLiveData.value = ResponseStates.Success(
                    loginUseCase.execute(email, password)
                )
            } catch (e: Exception) {
                _signInLiveData.value = ResponseStates.Failure(
                    e
                )
            }
        }
    }
}