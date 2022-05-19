package com.nexthotel_app.core.source.remote.network

import com.nexthotel_app.core.source.remote.response.HotelsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("hotel-data")
    suspend fun getHotels(
        @Query("page") page: Int? = 1,
        @Query("size") size: Int? = 10,
    ): HotelsResponse
}