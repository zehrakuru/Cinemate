package com.example.cinemate.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemate.common.Resource
import com.example.cinemate.data.model.Product
import com.example.cinemate.data.repository.ProductsRepository
import com.example.cinemate.ui.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState>
    get() = _detailState

    fun getProductDetail(id:Int) {
        viewModelScope.launch {

            when(val result = productsRepository.getProductDetail(id)) {
                is Resource.Success -> {
                    _detailState.value = DetailState.Data(result.data)
                }
                is Resource.Error -> {
                    _detailState.value = DetailState.Error(result.throwable)
                }
            }
        }
    }
}

sealed interface DetailState {
    data class Data(val product: Product) : DetailState
    data class Error(val throwable: Throwable) : DetailState
}