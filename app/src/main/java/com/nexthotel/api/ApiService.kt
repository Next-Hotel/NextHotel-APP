package com.nexthotel.api

import com.nexthotel.model.HotelsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("hotel-data")
    fun getListHotel(): Call<HotelsResponse>


    @GET("hotel-data")
    suspend fun searchHotel(@Query("name") keyword: String): Response<HotelsResponse>
}