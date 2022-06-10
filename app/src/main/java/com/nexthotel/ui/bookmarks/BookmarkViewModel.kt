package com.nexthotel.ui.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexthotel.core.data.HotelRepository
import com.nexthotel.core.data.local.entity.HotelEntity
import kotlinx.coroutines.launch

class BookmarkViewModel(private val hotelRepository: HotelRepository) : ViewModel() {

    fun getBookmarkedHotel() = hotelRepository.getBookmarkedHotel()

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