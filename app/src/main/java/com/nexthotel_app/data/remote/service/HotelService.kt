package com.nexthotel_app.data.remote.service

import com.nexthotel_app.data.remote.response.HotelResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface HotelServices {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("hotel-data")
    fun hotels(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<HotelResponse>

    @GET("hotel-data")
    fun hotelsSearch(@Query("name") keyword: String): Observable<HotelResponse>
}