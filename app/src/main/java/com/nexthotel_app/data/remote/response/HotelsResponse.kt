package com.nexthotel_app.data.remote.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HotelsResponse(

    @field:SerializedName("status")
    val status: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val listHotel: List<ListHotelItem>
)

@Parcelize
@Entity(tableName = "hotel")
data class ListHotelItem(

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("namaHotel")
    val namaHotel: String,

    @field:SerializedName("rating")
    val rating: String,

    @field:SerializedName("kota")
    val kota: String,

    @field:SerializedName("priceRange")
    val priceRange: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("latitude")
    val latitude: Double,

    @field:SerializedName("longitude")
    val longitude: Double,

    @field:SerializedName("imageUrl")
    val imageUrl: String,
) : Parcelable