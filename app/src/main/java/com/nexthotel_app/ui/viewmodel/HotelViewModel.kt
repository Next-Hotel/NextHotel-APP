package com.nexthotel_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nexthotel_app.core.repository.HotelRepository
import com.nexthotel_app.core.source.model.Hotel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(private val hotelRepository: HotelRepository) :
    ViewModel() {
    fun getHotelsBestForYou(): LiveData<PagingData<Hotel>> =
        hotelRepository.getHotelsBestForYou().cachedIn(viewModelScope)

    fun getHotelsSpecialOffers(): LiveData<PagingData<Hotel>> =
        hotelRepository.getHotelsSpecialOffers().cachedIn(viewModelScope)
}