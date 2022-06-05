package com.nexthotel_app.ui.main.detail

import com.nexthotel_app.data.local.hotel.HotelSchema
import com.nexthotel_app.repository.hotel.HotelFavRepository

class DetailUseCaseImplement(private val repository: HotelFavRepository) : DetailUseCase {
    override fun favorite(hotel: HotelSchema) = repository.favorite(hotel)
    override fun find(name: String) = repository.favorite(name)
    override fun delete(name: String) = repository.deleteFromFavorite(name)
}