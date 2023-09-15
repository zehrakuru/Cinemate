package com.example.cinemate.data.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.cinemate.common.Resource
import com.example.cinemate.data.model.GetMoviesResponse
import com.example.cinemate.data.model.GetProductDetailResponse
import com.example.cinemate.data.model.GetSaleMovieResponse
import com.example.cinemate.data.model.Product
import com.example.cinemate.data.source.remote.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsRepository(private val movieService: MovieService) {

    suspend fun getProducts(): Resource<List<Product?>> {
        return try {
            val result = movieService.getProducts().products

            if(result.isNullOrEmpty()) {
                Resource.Error(Exception("Movies are not found!"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSaleProducts() : Resource<List<Product?>> {
        return try {
            val result = movieService.getSaleProducts().products

            if(result.isNullOrEmpty()) {
                Resource.Error(Exception("Sale Movies are not found!"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getProductDetail(id:Int) : Resource<Product> {
        return try {

            val result = movieService.getProductDetail(id).product
            result?.let{
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Detail is not found!"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun searchProduct(query:String?) : Resource<List<Product?>> {

        return try {
            val result = movieService.searchProduct(query).products
            result?.let{
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("There is no such a movie!"))
            }
        } catch(e:Exception) {
            Resource.Error(e)
        }
    }

}