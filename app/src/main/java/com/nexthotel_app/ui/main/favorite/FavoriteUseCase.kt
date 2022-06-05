package com.nexthotel_app.ui.main.favorite

import com.nexthotel_app.data.local.hotel.HotelSchema
import io.reactivex.Completable
import io.reactivex.Flowable

interface FavoriteUseCase {
    fun list(): Flowable<List<HotelSchema>>
    fun truncate(): Completable
}