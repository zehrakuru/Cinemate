package com.example.cinemate.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemate.common.Resource
import com.example.cinemate.data.model.BaseResponse
import com.example.cinemate.data.model.DeleteFromCartRequest
import com.example.cinemate.data.model.Product
import com.example.cinemate.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState>
        get() = _cartState

    fun getCartProducts(userId: String) {
        viewModelScope.launch {
            val result = productsRepository.getCartProducts(userId)

            when(result) {
                is Resource.Success -> {
                    _cartState.value = CartState.Data(result.data)
                }
                is Resource.Error -> {
                    _cartState.value = CartState.Error(result.throwable)
                }
            }
        }
    }

    fun deleteFromCart(deleteFromCartRequest: DeleteFromCartRequest) {
        viewModelScope.launch {
            when(val result = productsRepository.deleteFromCart(deleteFromCartRequest)) {
                is Resource.Success -> {
                    _cartState.value = CartState.DeleteFromCart(result.data)
                }
                is Resource.Error -> {
                    _cartState.value = CartState.Error(result.throwable)
                }
            }


        }
    }

}

sealed interface CartState {
    data class Data(val products: List<Product?>) : CartState
    data class Error(val throwable: Throwable) : CartState
    data class DeleteFromCart(val baseResponse: BaseResponse) : CartState
}