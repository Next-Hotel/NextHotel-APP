package com.nexthotel_app.data.local.hotel.hotel_fav_entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = HotelFavLocalConfig.TABLE_FAVORITE)
data class HotelFavSchema(
    @ColumnInfo(name = "id") @PrimaryKey val id: Int?,
    @ColumnInfo(name = "namaHotel") var name: String?,
    @ColumnInfo(name = "rating") val rating: String?,
    @ColumnInfo(name = "kota") val city: String?,
    @ColumnInfo(name = "priceRange") val priceRange: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "latitude") val lat: Double?,
    @ColumnInfo(name = "longitude") val lon: Double?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
)