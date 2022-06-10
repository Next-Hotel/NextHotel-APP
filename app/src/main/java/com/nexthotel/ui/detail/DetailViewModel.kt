package com.nexthotel.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexthotel.core.data.HotelRepository
import com.nexthotel.core.data.local.entity.HotelEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val hotelRepository: HotelRepository) : ViewModel() {

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