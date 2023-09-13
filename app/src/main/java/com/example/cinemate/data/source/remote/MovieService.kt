package com.example.cinemate.data.source.remote

import com.example.cinemate.common.Constants.Endpoint.GET_PRODUCTS
import com.example.cinemate.common.Constants.Endpoint.GET_PRODUCT_DETAIL
import com.example.cinemate.common.Constants.Endpoint.GET_SALE_PRODUCTS
import com.example.cinemate.data.model.GetMoviesResponse
import com.example.cinemate.data.model.GetProductDetailResponse
import com.example.cinemate.data.model.GetSaleMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieService {

    @Headers("store: cinemate")
    @GET(GET_PRODUCTS)
    fun getProducts(): Call<GetMoviesResponse>

    @Headers("store: cinemate")
    @GET(GET_SALE_PRODUCTS)
    fun getSaleProducts() : Call<GetSaleMovieResponse>

    @Headers("store: cinemate")
    @GET(GET_PRODUCT_DETAIL)
    fun getProductDetail(
        @Query("id") id: Int
    ) : Call<GetProductDetailResponse>
}