package com.nexthotel_app.ui.main.detail

import com.nexthotel_app.data.local.hotel.hotel_entity.HotelFavoriteSchema
import com.nexthotel_app.data.local.hotel.HotelSchema
import com.nexthotel_app.repository.hotel.HotelRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class DetailUseCaseImpl(private val repository: HotelRepository) : DetailUseCase {
    override fun favorite(hotel: HotelSchema): Completable = repository.favorite(hotel)
    override fun delete(id: String): Completable = repository.deleteFromFavorite(id)
    override fun find(id: String): Flowable<HotelFavoriteSchema> = repository.favorite(id)
}