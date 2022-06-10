package com.nexthotel.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class HotelsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Hotel>,
)

data class Hotel(
    @SerializedName("id") val id: Int,
    @SerializedName("namaHotel") val name: String,
    @SerializedName("kota") val city: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("rating") val rate: String,
    @SerializedName("description") val description: String,
    @SerializedName("priceRange") val priceRange: String,
)
