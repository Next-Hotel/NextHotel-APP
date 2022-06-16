package com.nexthotel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexthotel.core.data.HotelRepository
import com.nexthotel.core.data.local.entity.HotelEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val hotelRepository: HotelRepository) : ViewModel() {

    fun getBestPick() = hotelRepository.getBestPick()

    fun getHotelForYou() = hotelRepository.getHotelForYou()

    fun saveHotel(hotel: HotelEntity) {
        viewModelScope.launch {
            hotelRepository.setBookmarkedHotel(hotel, true)
        }
    }

    fun deleteHotel(hotel: HotelEntity) {
        viewModelScope.launch {
            hotelRepository.setBookmarkedHotel(hotel, false)
        }
    }
}