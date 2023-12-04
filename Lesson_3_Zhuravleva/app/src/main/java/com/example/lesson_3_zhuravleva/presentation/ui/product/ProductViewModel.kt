package com.example.lesson_3_zhuravleva.presentation.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseProduct
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseStates
import com.example.lesson_3_zhuravleva.domain.product.GetProductUseCase
import com.example.lesson_3_zhuravleva.domain.product.ProductInfo
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
) : ViewModel() {

    private val _productLiveData = MutableLiveData<ResponseStates<ProductInfo>>()
    val productLiveData: LiveData<ResponseStates<ProductInfo>> = _productLiveData

    fun getProduct(id: String) {
        viewModelScope.launch {
            _productLiveData.value = ResponseStates.Loading()
            try {
                _productLiveData.value = ResponseStates.Success(
                    getProductUseCase.execute(id)
                )
            } catch (e: Exception) {
                _productLiveData.value = ResponseStates.Failure(
                    e
                )
            }
        }
    }
}