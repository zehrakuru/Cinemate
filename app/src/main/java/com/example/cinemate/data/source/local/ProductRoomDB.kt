package com.example.cinemate.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinemate.data.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductRoomDB : RoomDatabase() {

    abstract fun productsDao(): ProductDao
}