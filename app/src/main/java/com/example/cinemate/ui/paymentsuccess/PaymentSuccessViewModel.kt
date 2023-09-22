package com.example.cinemate.ui.paymentsuccess

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemate.common.Resource
import com.example.cinemate.data.model.BaseResponse
import com.example.cinemate.data.model.ClearCartRequest
import com.example.cinemate.data.model.Product
import com.example.cinemate.data.model.ProductUI
import com.example.cinemate.data.repository.ProductsRepository
import com.example.cinemate.ui.cart.CartState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentSuccessViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private var _successState = MutableLiveData<SuccessState>()
    val successState: LiveData<SuccessState>
        get() = _successState

    private lateinit var auth: FirebaseAuth

    private val _totalPriceAmount = MutableLiveData(0.0)
    val totalPriceAmount: LiveData<Double> = _totalPriceAmount

    private fun getCartProducts(userId: String) {
        viewModelScope.launch {
            val result = productsRepository.getCartProducts(userId)

            when(result) {
                is Resource.Success -> {
                    _successState.value = SuccessState.Data(result.data)
                    _totalPriceAmount.value = result.data.sumOf {
                        it.price ?: 0.0
                    }
                }
                is Resource.Error -> {
                    _successState.value = SuccessState.Error(result.throwable)
                    resetTotalAmount()
                }
            }
        }
    }

    fun clearCart(userId: String) {
        auth = Firebase.auth
        viewModelScope.launch {
            val clearCartRequest = ClearCartRequest(userId)
            when(val result = productsRepository.clearCart(clearCartRequest)) {
                is Resource.Success -> {
                    _successState.value = SuccessState.ClearCart(result.data)
                    getCartProducts(auth.currentUser?.uid.toString())
                }
                is Resource.Error -> {
                    _successState.value = SuccessState.Error(result.throwable)
                }
            }
        }
    }

    private fun resetTotalAmount() {
        _totalPriceAmount.value = 0.0
    }
}

sealed interface SuccessState {
    data class Data(val products: List<ProductUI>) : SuccessState
    data class Error(val throwable: Throwable) : SuccessState
    data class ClearCart(val baseResponse: BaseResponse) : SuccessState
}