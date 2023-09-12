package com.example.cinemate.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinemate.MainApplication
import com.example.cinemate.R
import com.example.cinemate.data.model.GetMoviesResponse
import com.example.cinemate.data.model.GetSaleMovieResponse
import com.example.cinemate.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val movieAdapter by lazy {MovieAdapter()}
    private val saleMovieAdapter by lazy { SaleMovieAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSecond.adapter = movieAdapter
        getProducts()
        binding.rvFirst.adapter = saleMovieAdapter
        getSaleProducts()
    }

    fun getProducts() {
        MainApplication.movieService?.getProducts()?.enqueue(object : Callback<GetMoviesResponse> {
            override fun onResponse(
                call: Call<GetMoviesResponse>,
                response: Response<GetMoviesResponse>
            ) {
                val result = response.body()?.products

                if (result.isNullOrEmpty().not()) {
                    movieAdapter.submitList(result)
                }
            }

            override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getSaleProducts() {
        MainApplication.movieService?.getSaleProducts()?.enqueue(object: Callback<GetSaleMovieResponse> {
            override fun onResponse(
                call: Call<GetSaleMovieResponse>,
                response: Response<GetSaleMovieResponse>
            ) {
                val result = response.body()?.products

                if(result.isNullOrEmpty().not()) {
                    saleMovieAdapter.submitList(result)
                }
            }

            override fun onFailure(call: Call<GetSaleMovieResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
}