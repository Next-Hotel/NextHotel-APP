package com.nexthotel.ui.survey

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexthotel.core.data.HotelRepository

class SurveyViewModel(private val hotelRepository: HotelRepository) : ViewModel() {
    var result = MutableLiveData<List<String>>()
    private val list: List<String> = listOf(
        "Nightlife",
        "Food Court",
        "Fast Food",
        "Hotel Service",
        "Business",
        "Night Life",
        "Transportation",
        "Cafe",
        "Connectivity"
    )

    init {
        getListSurvey()
    }

    private fun getListSurvey(){
        result.value = list
    }
}