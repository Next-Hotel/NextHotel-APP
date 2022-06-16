package com.nexthotel.core.data.remote.network

import com.nexthotel.core.data.remote.response.HotelsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("hotel-data")
    suspend fun getExploreHotel(): HotelsResponse

    @GET("hotel-data/getBestPicks")
    suspend fun getBestPicksHotels(): HotelsResponse

    @GET("hotel-data/getRecomendation")
    suspend fun getRecommendationHotels(): HotelsResponse

    @GET("hotel-data")
    suspend fun searchHotel(
        @Query("name") name: String
    ): Response<HotelsResponse>
}