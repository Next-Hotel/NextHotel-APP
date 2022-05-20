package com.nexthotel_app.repository

import com.nexthotel_app.repository.hotel.HotelRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { HotelRepository(get(), get()) }
}