package com.nexthotel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexthotel.core.data.HotelRepository
import com.nexthotel.core.data.local.entity.HotelEntity
import kotlinx.coroutines.launch

class HomeViewModel(private val hotelRepository: HotelRepository) : ViewModel() {

    fun getBestPick() = hotelRepository.getBestPick()

    //changing hotel for you to recommendation with survey
    fun getHotelForYou(interest: Set<String>) = hotelRepository.getRecommendation(interest)

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