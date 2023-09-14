package com.example.cinemate.data.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.cinemate.data.model.GetMoviesResponse
import com.example.cinemate.data.model.GetProductDetailResponse
import com.example.cinemate.data.model.GetSaleMovieResponse
import com.example.cinemate.data.model.Product
import com.example.cinemate.data.source.remote.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsRepository(private val movieService: MovieService) {

    val productsLiveData = MutableLiveData<List<Product?>?>()
    val saleProductsLiveData = MutableLiveData<List<Product?>?>()
    val productDetailLiveData = MutableLiveData<Product?>()
    val errorMessageLiveData = MutableLiveData<String>()

    suspend fun getProducts() {
        val result = movieService.getProducts().products

        if (result.isNullOrEmpty().not()) {
            productsLiveData.value = result
        } else {
            productsLiveData.value = null
        }
    }

    suspend fun getSaleProducts() {
        val result = movieService.getSaleProducts().products

        if(result.isNullOrEmpty().not()) {
            saleProductsLiveData.value = result
        } else {
            saleProductsLiveData.value = null
        }
    }

    suspend fun getProductDetail(id:Int) {

        productDetailLiveData.value = movieService.getProductDetail(id).product

    }
}