package com.nexthotel_app.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nexthotel_app.data.remote.response.ListHotelItem

@Database(
    entities = [ListHotelItem::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class HotelDatabase : RoomDatabase() {
    abstract fun hotelDao(): HotelDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: HotelDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): HotelDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    HotelDatabase::class.java, "hotel.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}