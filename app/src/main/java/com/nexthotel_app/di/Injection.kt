package com.nexthotel_app.di

import HotelDatabase
import HotelRepository
import android.content.Context
import com.nexthotel_app.data.remote.retrofit.ApiConfig

object Injection {
    fun provideStoryRepository(context: Context): HotelRepository {
        val database = HotelDatabase.getInstance(context)
        val apiService = ApiConfig.getApiService()
        return HotelRepository(database, apiService)
    }
}