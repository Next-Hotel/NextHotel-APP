package com.nexthotel.core.di

import android.content.Context
import com.nexthotel.core.data.HotelRepository
import com.nexthotel.core.data.local.room.HotelDatabase
import com.nexthotel.core.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): HotelRepository {
        val apiService = ApiConfig.getApiService()
        val database = HotelDatabase.getInstance(context)
        val dao = database.hotelDao()
        return HotelRepository.getInstance(apiService, dao)
    }
}