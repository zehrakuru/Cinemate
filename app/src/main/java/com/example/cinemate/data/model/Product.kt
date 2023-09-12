package com.example.cinemate.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    val category: String?,
    val count: Int?,
    val description: String?,
    val id: Int?,
    @SerializedName("imageOne")
    val imageOne: String?,
    @SerializedName("imageThree")
    val imageThree: String?,
    @SerializedName("imageTwo")
    val imageTwo: String?,
    val price: Double?,
    val rate: Double?,
    val salePrice: Double?,
    val saleState: Boolean?,
    val title: String?
)
