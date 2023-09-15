package com.example.cinemate.data.model

data class SearchProductResponse(
    val message: String?,
    val products: List<Product?>?,
    val status: Int?
)
