package com.nexthotel.ui.explore

import androidx.lifecycle.ViewModel
import com.nexthotel.core.data.HotelRepository

class ExploreViewModel(private val repo: HotelRepository) : ViewModel() {
    fun getExplore() = repo.getExplore()
}