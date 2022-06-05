package com.nexthotel_app.repository.hotel

import com.nexthotel_app.data.local.hotel.HotelSchema
import com.nexthotel_app.data.local.hotel.toHotelFavorite
import com.nexthotel_app.data.local.hotel.hotel_fav_entity.HotelFavDao
import com.nexthotel_app.data.remote.service.HotelServices

class HotelFavRepository(
    private val dao: HotelFavDao,
    private val service: HotelServices
) {
    fun favorite(hotel: HotelSchema) = dao.save(hotel.toHotelFavorite())

    fun favorite(name: String) = dao.find(name)

    fun favorites() = dao.findAll()

    fun deleteFromFavorite(name: String) = dao.delete(name)

    fun truncateFavorite() = dao.truncate()
}