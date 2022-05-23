package com.nexthotel_app.data.local.hotel.hotel_entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.nexthotel_app.data.local.hotel.hotel_fav_entity.HotelFavSchema
import io.reactivex.Flowable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "hotel")
data class HotelSchema(
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int?,

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

fun HotelSchema.toHotelFavorite() = HotelFavSchema(
    id = this.id,
    name = this.name,
    rating = this.rating,
    city = this.city,
    priceRange = this.priceRange,
    description = this.description,
    lat = this.lat,
    lon = this.lon,
    imageUrl = this.imageUrl,
)

fun List<HotelFavSchema>.toListHotel(): Flowable<List<HotelSchema>> {
    return Flowable.just(this.map { item ->
        HotelSchema(
            id = item.id,
            name = item.name,
            rating = item.rating,
            city = item.city,
            priceRange = item.priceRange,
            description = item.description,
            lat = item.lat,
            lon = item.lon,
            imageUrl = item.imageUrl,
        )
    })
}