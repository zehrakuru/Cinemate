package com.example.cinemate.data.mapper

import com.example.cinemate.data.model.Product
import com.example.cinemate.data.model.ProductEntity
import com.example.cinemate.data.model.ProductUI

fun ProductEntity.mapToProductUI() : ProductUI {
    return ProductUI(
        category = category.orEmpty(),
        count = count ?: 1,
        description = description.orEmpty(),
        id = id ?: 1,
        imageOne = imageOne.orEmpty(),
        imageThree = imageThree.orEmpty(),
        imageTwo = imageTwo.orEmpty(),
        price = price ?: 0.0,
        rate = rate ?: 0.0,
        salePrice = salePrice ?: 0.0,
        saleState = saleState ?: false,
        title = title.orEmpty(),
    )
}

fun Product.mapToProductUI(): ProductUI {
    return ProductUI(
        category = category.orEmpty(),
        count = count ?: 1,
        description = description.orEmpty(),
        id = id ?: 1,
        imageOne = imageOne.orEmpty(),
        imageThree = imageThree.orEmpty(),
        imageTwo = imageTwo.orEmpty(),
        price = price ?: 0.0,
        rate = rate ?: 0.0,
        salePrice = salePrice ?: 0.0,
        saleState = saleState ?: false,
        title = title.orEmpty()
    )
}