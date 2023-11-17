package com.example.lesson_3_zhuravleva.presentation.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseStates
import com.example.lesson_3_zhuravleva.domain.catalog.GetProductsUseCase
import com.example.lesson_3_zhuravleva.domain.catalog.Product
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _productsLiveData = MutableLiveData<ResponseStates<List<Product>>>()
    val productsLiveData: LiveData<ResponseStates<List<Product>>> = _productsLiveData

    fun getProductsList() {
        viewModelScope.launch {
            _productsLiveData.value = ResponseStates.Loading()
            try {
                _productsLiveData.value = ResponseStates.Success(
                    getProductsUseCase.execute()
                )
            } catch (e: Exception) {
                _productsLiveData.value = ResponseStates.Failure(
                    e
                )
            }
        }
    }

    init{
        getProductsList()
    }

}