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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState>
        get() = _cartState

    private lateinit var auth: FirebaseAuth

    private val _totalPriceAmount = MutableLiveData(0.0)
    val totalPriceAmount: LiveData<Double> = _totalPriceAmount

    fun getCartProducts(userId: String) {
        viewModelScope.launch {
            val result = productsRepository.getCartProducts(userId)

            when(result) {
                is Resource.Success -> {
                    _cartState.value = CartState.Data(result.data)
                    _totalPriceAmount.value = result.data.sumOf {
                        it.price ?: 0.0
                    }
                }
                is Resource.Error -> {
                    _cartState.value = CartState.Error(result.throwable)
                    resetTotalAmount()
                }
            }
        }
    }

    fun deleteFromCart(id: Int) {
        auth = Firebase.auth
        viewModelScope.launch {
            val deleteFromCartRequest = DeleteFromCartRequest(id)
            when(val result = productsRepository.deleteFromCart(deleteFromCartRequest)) {
                is Resource.Success -> {
                    _cartState.value = CartState.DeleteFromCart(result.data)
                    getCartProducts(auth.currentUser?.uid.toString())
                }
                is Resource.Error -> {
                    _cartState.value = CartState.Error(result.throwable)
                }
            }
        }
    }

    fun increase(price: Double?) {
        _totalPriceAmount.value = price?.let { _totalPriceAmount.value?.plus(it) }
    }

    fun decrease(price: Double?) {
        _totalPriceAmount.value = price?.let { _totalPriceAmount.value?.minus(it) }
    }

    fun resetTotalAmount() {
        _totalPriceAmount.value = 0.0
    }

}

sealed interface CartState {
    data class Data(val products: List<Product?>) : CartState
    data class Error(val throwable: Throwable) : CartState
    data class DeleteFromCart(val baseResponse: BaseResponse) : CartState
}