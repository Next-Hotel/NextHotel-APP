package com.nexthotel_app.data.local

import androidx.room.Room
import com.nexthotel_app.data.local.hotel.hotel_fav_entity.HotelFavDatabase
import com.nexthotel_app.data.local.hotel.hotel_fav_entity.HotelFavLocalConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val daoFavModule = module {
    factory { get<HotelFavDatabase>().hotelFav() }
}

@Volatile
private var INSTANCE: HotelFavDatabase? = null

val localFavModule = module {
    single {
        INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                androidApplication(),
                HotelFavDatabase::class.java, HotelFavLocalConfig.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
    }
}