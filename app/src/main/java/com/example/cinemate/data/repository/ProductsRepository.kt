package com.example.cinemate.data.repository

import com.example.cinemate.common.Resource
import com.example.cinemate.data.mapper.mapToProductEntity
import com.example.cinemate.data.mapper.mapToProductUI
import com.example.cinemate.data.model.*
import com.example.cinemate.data.source.local.ProductDao
import com.example.cinemate.data.source.remote.MovieService

class ProductsRepository(
    private val movieService: MovieService,
    private val productDao: ProductDao) {

    suspend fun getProducts(): Resource<List<ProductUI>> {
        return try {
            val favoriteNamesList = getFavoritesNamesList()
            val result = movieService.getProducts().products.orEmpty()
            if(result.isEmpty()) {
                Resource.Error(Exception("Movies are not found!"))
            } else {
                Resource.Success(result.map {
                    it.mapToProductUI(isFavorite = favoriteNamesList.contains(it.title))
                })
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSaleProducts() : Resource<List<ProductUI>> {
        return try {
            val favoriteNamesList = getFavoritesNamesList()
            val result = movieService.getSaleProducts().products.orEmpty()

            if(result.isEmpty()) {
                Resource.Error(Exception("Sale Movies are not found!"))
            } else {
                Resource.Success(result.map {
                    it.mapToProductUI(isFavorite = favoriteNamesList.contains(it.title))
                })
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getProductDetail(id:Int) : Resource<ProductUI> {
        return try {

            val favoriteNamesList = getFavoritesNamesList()
            val result = movieService.getProductDetail(id).product
            result.let{
                Resource.Success(it.mapToProductUI(isFavorite = favoriteNamesList.contains(it.title)))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun searchProduct(query:String?) : Resource<List<ProductUI>> {

        return try {
            val favoriteNamesList = getFavoritesNamesList()
            val result = movieService.searchProduct(query).products
            result?.let{
                Resource.Success(result.map {
                    it.mapToProductUI(isFavorite = favoriteNamesList.contains(it.title))
                })
            } ?: kotlin.run {
                Resource.Error(Exception("There is no such a movie!"))
            }
        } catch(e:Exception) {
            Resource.Error(e)
        }
    }

    suspend fun addToCart(addToCartRequest: AddToCartRequest) : Resource<BaseResponse> {
        return try {
            val result = movieService.addToCart(addToCartRequest)

            if(result.status==200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception(result.message.toString()))
            }
        } catch(e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun deleteFromCart(deleteFromCartRequest: DeleteFromCartRequest) : Resource<BaseResponse> {
        return try {
            val result = movieService.deleteFromCart(deleteFromCartRequest)

            if(result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception(result.message.toString()))
            }
        } catch(e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun clearCart(clearCartRequest: ClearCartRequest) : Resource<BaseResponse> {
        return try {
            val result = movieService.clearCart(clearCartRequest)

            if(result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception(result.message.toString()))
            }
        } catch(e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getCartProducts(userId:String?) : Resource<List<ProductUI>> {
        return try {
            val favoriteNamesList = getFavoritesNamesList()
            val result = movieService.getCartProducts(userId)

            if(result.status == 200) {
                Resource.Success(result.products.orEmpty().map {
                    it.mapToProductUI(isFavorite = favoriteNamesList.contains(it.title))
                })
            } else {
                Resource.Error(Exception(result.message.orEmpty()))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun addToFavorites(product: ProductUI) {
        productDao.addToFavorite(product.mapToProductEntity())
    }

    suspend fun deleteProductFromFavorite(product: ProductUI) {
        productDao.deleteFavProduct(product.mapToProductEntity())
    }

    suspend fun getFavoritesNamesList() = productDao.getFavoritesTitles()

    suspend fun getFavoriteProducts(): Resource<List<ProductUI>> {
        return try {
            val result = productDao.getFavorites().map { it.mapToProductUI() }

            if(result.isEmpty()) {
                Resource.Error(Exception("There are no products here!"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}