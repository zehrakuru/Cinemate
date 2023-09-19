package com.example.cinemate.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemate.common.Resource
import com.example.cinemate.data.model.ProductUI
import com.example.cinemate.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel  @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private var _favState = MutableLiveData<FavState>()
    val favState: LiveData<FavState>
        get() = _favState

    fun getFavoriteProducts() {
        viewModelScope.launch {
            when (val result = productsRepository.getFavoriteProducts()) {
                is Resource.Success -> {
                    _favState.value = FavState.Data(result.data)
                }

                is Resource.Error -> {
                    _favState.value = FavState.Error(result.throwable)
                }
            }
        }
    }

    fun deleteProductFromFavorites(product: ProductUI) {
        viewModelScope.launch {
            productsRepository.deleteProductFromFavorite(product)
            getFavoriteProducts()
        }
    }
}

sealed interface FavState {
    data class Data(val products: List<ProductUI>) : FavState
    data class Error(val throwable: Throwable) : FavState
}