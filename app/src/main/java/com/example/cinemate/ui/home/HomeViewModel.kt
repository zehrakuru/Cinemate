package com.example.cinemate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemate.common.Resource
import com.example.cinemate.data.model.Product
import com.example.cinemate.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState

    fun getProducts() {
        viewModelScope.launch {

            val result = productsRepository.getProducts()

            when (result) {
                is Resource.Success -> {
                    _homeState.value = HomeState.Data(result.data)
                }

                is Resource.Error -> {
                    _homeState.value = HomeState.Error(result.throwable)
                }
            }
        }
    }

     fun getSaleProducts() {
        viewModelScope.launch {
            //productsRepository.getSaleProducts()
            val result = productsRepository.getSaleProducts()

            when(result) {
                is Resource.Success -> {
                    _homeState.value = HomeState.SaleData(result.data)
                }

                is Resource.Error -> {
                    _homeState.value = HomeState.Error(result.throwable)
                }
            }
        }

    }
}

sealed interface HomeState {
    data class Data(val products: List<Product?>) : HomeState
    data class SaleData(val products: List<Product?>) : HomeState
    data class Error(val throwable: Throwable) : HomeState
}