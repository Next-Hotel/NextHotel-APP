package com.nexthotel.ui.home

import androidx.lifecycle.ViewModel
import com.nexthotel.core.data.HotelRepository

class HomeViewModel(private val repo: HotelRepository) : ViewModel() {
    fun getHotelForYou(interest: Set<String>) = repo.getHotelForYou(interest)
    fun getBestPick() = repo.getBestPick()
}