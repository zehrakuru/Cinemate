package com.example.cinemate.data.model


import com.google.gson.annotations.SerializedName

data class GetSaleMovieResponse(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)