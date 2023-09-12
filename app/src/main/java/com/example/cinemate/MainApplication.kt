package com.example.cinemate

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.cinemate.common.Constants.BASE_URL
import com.example.cinemate.data.source.remote.MovieService
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//@HiltAndroidApp
class MainApplication : Application() {
    companion object {

        var movieService: MovieService? = null

        private const val TIMEOUT = 60L

        fun provideRetrofit(context: Context) {

            val chuckerInterceptor = ChuckerInterceptor.Builder(context).build()

            val okHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(chuckerInterceptor)
                readTimeout(TIMEOUT, TimeUnit.SECONDS)
                connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            }.build()

            movieService = Retrofit.Builder().apply {
                addConverterFactory(GsonConverterFactory.create())
                baseUrl(BASE_URL)
                client(okHttpClient)
            }.build().create(MovieService::class.java)
        }
    }
}