package com.example.cinemate.data.model

data class ProductUI(
    val category: String,
    val count: Int,
    val description: String,
    val id: Int,
    val imageOne: String,
    val imageThree: String,
    val imageTwo: String,
    val price: Double,
    val rate: Double,
    val salePrice: Double,
    val saleState: Boolean,
    val title: String
) {
    fun mapToProductEntity(): ProductEntity {
        return ProductEntity(
            category = category,
            count = count,
            description = description,
            id = id,
            imageOne = imageOne,
            imageThree = imageThree,
            imageTwo = imageTwo,
            price = price,
            rate = rate,
            salePrice = salePrice,
            saleState = saleState,
            title = title
        )
    }
}
