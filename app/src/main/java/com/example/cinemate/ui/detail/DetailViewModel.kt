package com.example.cinemate.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinemate.data.model.Product
import com.example.cinemate.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private var _productDetailLiveData = MutableLiveData<Product?>()
    val productDetailLiveData: LiveData<Product?>
    get() = _productDetailLiveData

    private var _errorMessageLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
    get() = _errorMessageLiveData

    init {
        _productDetailLiveData = productsRepository.productDetailLiveData
        _errorMessageLiveData = productsRepository.errorMessageLiveData
    }

    fun getProductDetail(id:Int) {
        productsRepository.getProductDetail(id)
    }

}