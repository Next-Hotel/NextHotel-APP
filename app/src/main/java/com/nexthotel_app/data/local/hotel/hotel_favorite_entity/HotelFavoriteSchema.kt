package com.nexthotel_app.data.local.hotel.hotel_entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "hotel_favorite")
data class HotelFavoriteSchema(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("namaHotel")
    val name: String? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("kota")
    val city: String? = null,

    @field:SerializedName("priceRange")
    val priceRange: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("latitude")
    val lat: Double? = null,

    @field:SerializedName("longitude")
    val lon: Double? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,
) : Parcelable