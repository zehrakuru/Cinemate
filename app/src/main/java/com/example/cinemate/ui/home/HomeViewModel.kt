package com.example.cinemate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinemate.data.model.Product
import com.example.cinemate.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private var _productsLiveData = MutableLiveData<List<Product?>?>()
    val productsLiveData: LiveData<List<Product?>?>
    get() = _productsLiveData

    private var _saleProductsLiveData = MutableLiveData<List<Product?>?>()
    val saleProductsLiveData: LiveData<List<Product?>?>
    get() = _saleProductsLiveData

    private var _errorMessageLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
    get() = _errorMessageLiveData

    init {
        _productsLiveData = productsRepository.productsLiveData
        _saleProductsLiveData = productsRepository.saleProductsLiveData
        _errorMessageLiveData = productsRepository.errorMessageLiveData
    }
    fun getProducts() {
        productsRepository.getProducts()
    }

    fun getSaleProducts() {
        productsRepository.getSaleProducts()
    }
}