package com.nexthotel_app.data.remote.retrofit

import com.nexthotel_app.data.remote.response.HotelsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("hotel-data")
    suspend fun getHotels(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): HotelsResponse
}