package com.nexthotel.api

import com.nexthotel.model.HotelsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("hotel-data")
    fun getListHotel(): Call<HotelsResponse>
}