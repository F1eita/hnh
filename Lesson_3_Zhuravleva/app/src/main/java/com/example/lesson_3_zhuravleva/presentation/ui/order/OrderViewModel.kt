package com.example.lesson_3_zhuravleva.presentation.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseStates
import com.example.lesson_3_zhuravleva.domain.order.Order
import com.example.lesson_3_zhuravleva.domain.order.CreatedOrderInfo
import com.example.lesson_3_zhuravleva.domain.order.OrderUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderViewModel @Inject constructor(
    private val orderUseCase: OrderUseCase,
) : ViewModel() {

    private val _orderLiveData = MutableLiveData<ResponseStates<CreatedOrderInfo>>()
    val orderLiveData: LiveData<ResponseStates<CreatedOrderInfo>> = _orderLiveData

    fun makeOrder(order: Order) {
        viewModelScope.launch {
            _orderLiveData.value = ResponseStates.Loading()
            try {
                _orderLiveData.value = ResponseStates.Success(
                    orderUseCase.execute(order)
                )
            } catch (e: Exception) {
                _orderLiveData.value = ResponseStates.Failure(
                    e
                )
            }
        }
    }

}