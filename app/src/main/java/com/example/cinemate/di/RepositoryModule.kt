package com.example.cinemate.di

import com.example.cinemate.data.repository.ProductsRepository
import com.example.cinemate.data.source.remote.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideProductRepository(movieService: MovieService): ProductsRepository =
        ProductsRepository(movieService)
}