package com.nexthotel.core.di

import android.content.Context
import com.nexthotel.core.data.HotelRepository
import com.nexthotel.core.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): HotelRepository {
        val remoteData = ApiConfig.getApiService()
        return HotelRepository.getInstance(remoteData)
    }
}