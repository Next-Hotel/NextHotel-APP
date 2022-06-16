package com.nexthotel.core.data.remote.network

import com.nexthotel.core.data.remote.response.HotelsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("hotel-bestpicks")
    suspend fun getHotelForYou(): HotelsResponse

    @GET("hotel-bestpicks")
    suspend fun getBestPick(): HotelsResponse

    @GET("hotel-bestpicks")
    suspend fun getExploreHotel(): HotelsResponse

    @GET("hotel-bestpicks")
    suspend fun searchHotel(
        @Query("name") name: String
    ): Response<HotelsResponse>
}