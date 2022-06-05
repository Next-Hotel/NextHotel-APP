package com.gonexwind.nexthotel.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HotelsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Hotel>,
)

@Parcelize
data class Hotel(
    @SerializedName("id") val id: Int,
    @SerializedName("namaHotel") val name: String,
    @SerializedName("kota") val city: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("rating") val rate: String,
    @SerializedName("description") val description: String,
    @SerializedName("priceRange") val priceRange: String,
): Parcelable
