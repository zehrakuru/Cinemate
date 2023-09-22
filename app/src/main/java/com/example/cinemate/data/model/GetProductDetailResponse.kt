package com.example.cinemate.data.model

data class GetProductDetailResponse(
    val message: String?,
    val product: Product,
    val status: Int?
)
