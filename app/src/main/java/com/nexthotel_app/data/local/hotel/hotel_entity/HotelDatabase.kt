package com.nexthotel_app.data.local.hotel.hotel_entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelDao
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import com.nexthotel_app.data.local.hotel.remote_keys_entity.RemoteKeysDao
import com.nexthotel_app.data.local.hotel.remote_keys_entity.RemoteKeysSchema

@Database(
    entities = [
        HotelSchema::class,
        RemoteKeysSchema::class
    ], version = 2, exportSchema = false
)
abstract class HotelDatabase : RoomDatabase() {
    abstract fun hotel(): HotelDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}