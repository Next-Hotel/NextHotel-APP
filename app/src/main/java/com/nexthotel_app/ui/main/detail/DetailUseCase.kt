package com.nexthotel_app.ui.main.detail

import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import com.nexthotel_app.data.local.hotel.hotel_fav_entity.HotelFavSchema
import io.reactivex.Completable
import io.reactivex.Flowable

interface DetailUseCase {
    fun favorite(hotel: HotelSchema): Completable
    fun find(name: String): Flowable<HotelFavSchema>
    fun delete(name: String): Completable
}