package com.nexthotel.core.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nexthotel.core.data.local.entity.HotelEntity

@Database(entities = [HotelEntity::class], version = 1, exportSchema = false)
abstract class HotelDatabase : RoomDatabase() {

    abstract fun hotelDao(): HotelDao

    companion object {
        @Volatile
        private var instance: HotelDatabase? = null

        fun getInstance(context: Context): HotelDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    HotelDatabase::class.java, "News.db"
                ).build()
            }
        }
    }
}