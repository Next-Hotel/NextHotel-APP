package com.nexthotel.core.data.remote.network

import com.nexthotel.core.data.remote.response.HotelsResponse
import com.nexthotel.core.data.remote.response.InterestResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("hotel-bestpicks")
    suspend fun getHotelForYou(): HotelsResponse

    @FormUrlEncoded
    @POST("recommendation")
    suspend fun getHotelRecommendation(
        @Field("interest[]") interest: Set<String>
    ): HotelsResponse

    @GET("hotel-bestpicks")
    suspend fun getBestPick(): HotelsResponse

    @GET("hotel-bestpicks")
    suspend fun getExploreHotel(): HotelsResponse

    @GET("hotel-bestpicks")
    suspend fun searchHotel(
        @Query("name") name: String
    ): Response<HotelsResponse>

    @GET("hotel-data/survey")
    suspend fun getInterestParameter(
    ): InterestResponse
}