package com.gonexwind.nexthotel.api

import com.gonexwind.nexthotel.model.HotelsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("hotel-data")
    fun getListHotel(): Call<HotelsResponse>
}