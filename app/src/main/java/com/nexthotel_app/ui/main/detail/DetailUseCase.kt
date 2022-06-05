package com.nexthotel_app.ui.main.detail

import com.nexthotel_app.data.local.hotel.hotel_entity.HotelFavoriteSchema
import com.nexthotel_app.data.local.hotel.HotelSchema
import io.reactivex.Completable
import io.reactivex.Flowable

interface DetailUseCase {
    fun favorite(hotel: HotelSchema): Completable
    fun delete(id: String): Completable
//    fun detail(id: String): Observable<HotelSchema>
    fun find(id: String): Flowable<HotelFavoriteSchema>

}