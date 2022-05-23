package com.nexthotel_app.data.local.hotel.hotel_fav_entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        HotelFavSchema::class
    ], version = 1, exportSchema = false
)
abstract class HotelFavDatabase : RoomDatabase() {
    abstract fun hotelFav(): HotelFavDao
}