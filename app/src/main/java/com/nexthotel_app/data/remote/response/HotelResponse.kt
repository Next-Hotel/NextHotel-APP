package com.nexthotel_app.data.remote.response

import com.google.gson.annotations.SerializedName
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import io.reactivex.Observable

data class HotelResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val listHotel: List<HotelSchema> = emptyList()
)

fun HotelResponse.mapToList() = Observable.just(this.listHotel)