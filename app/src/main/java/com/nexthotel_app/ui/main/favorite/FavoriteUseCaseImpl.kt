package com.nexthotel_app.ui.main.favorite

import com.nexthotel_app.data.local.hotel.HotelSchema
import com.nexthotel_app.data.local.hotel.toListHotel
import com.nexthotel_app.repository.hotel.HotelRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class FavoriteUseCaseImpl(private val repository: HotelRepository) : FavoriteUseCase {
    override fun list(): Flowable<List<HotelSchema>> = repository.favorites()
        .subscribeOn(Schedulers.io())
        .flatMap { database -> database.toListHotel() }

    override fun truncate() = repository.truncateFavorite()

}