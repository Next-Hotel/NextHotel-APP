package com.nexthotel_app.ui.main.favorite

import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface FavoriteUseCase {
    fun list(): Flowable<List<HotelSchema>>
    fun truncate(): Completable
}