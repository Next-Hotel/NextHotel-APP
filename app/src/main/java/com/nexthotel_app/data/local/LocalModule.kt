package com.nexthotel_app.data.local

import androidx.room.Room
import com.nexthotel_app.data.local.hotel.HotelDatabase
import com.nexthotel_app.data.local.hotel.HotelLocalConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val daoModule = module {
    factory { get<HotelDatabase>().hotel() }
    factory { get<HotelDatabase>().remoteKeysDao() }
}

@Volatile
private var INSTANCE: HotelDatabase? = null

val localModule = module {
    single {
        INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                androidApplication(),
                HotelDatabase::class.java, HotelLocalConfig.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
    }
}