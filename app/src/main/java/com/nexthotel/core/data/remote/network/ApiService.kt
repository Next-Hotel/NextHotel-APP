package com.nexthotel.core.data.remote.network

import com.nexthotel.core.data.remote.response.HotelsResponse
import com.nexthotel.core.data.remote.response.InterestResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("recommendation?limit=10")
    suspend fun getHotelForYou(
        @Field("interest[]") interest: Set<String>
    ): HotelsResponse

    @GET("hotel-bestpicks?limit=10")
    suspend fun getBestPick(): HotelsResponse

    @GET("list-hotels")
    suspend fun getExploreHotel(): HotelsResponse

    @GET("list-hotels")
    suspend fun searchHotel(
        @Query("name") name: String
    ): Response<HotelsResponse>

    @GET("hotel-data/survey")
    suspend fun getInterestParameter(): InterestResponse
}