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

    fun getProducts() {
        movieService.getProducts().enqueue(object : Callback<GetMoviesResponse> {
            override fun onResponse(
                call: Call<GetMoviesResponse>,
                response: Response<GetMoviesResponse>
            ) {
                val result = response.body()?.products

                if (result.isNullOrEmpty().not()) {
                    productsLiveData.value = result
                } else {
                    productsLiveData.value = null
                }
            }

            override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                errorMessageLiveData.value = t.message.orEmpty()
            }

        })
    }

    fun getSaleProducts() {
        movieService.getSaleProducts().enqueue(object: Callback<GetSaleMovieResponse> {
            override fun onResponse(
                call: Call<GetSaleMovieResponse>,
                response: Response<GetSaleMovieResponse>
            ) {
                val result = response.body()?.products

                if(result.isNullOrEmpty().not()) {
                    saleProductsLiveData.value = result
                } else {
                    saleProductsLiveData.value = result
                }
            }

            override fun onFailure(call: Call<GetSaleMovieResponse>, t: Throwable) {
                errorMessageLiveData.value = t.message.orEmpty()
            }

        }
        )
    }

    fun getProductDetail(id:Int) {
        movieService.getProductDetail(id).enqueue(object : Callback<GetProductDetailResponse> {
            override fun onResponse(
                call: Call<GetProductDetailResponse>,
                response: Response<GetProductDetailResponse>
            ) {
                productDetailLiveData.value = response.body()?.product
            }

            override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                errorMessageLiveData.value = t.message.orEmpty()
            }

        })
    }
}