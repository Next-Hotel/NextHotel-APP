package com.nexthotel.ui.survey

import androidx.lifecycle.ViewModel
import com.nexthotel.core.data.HotelRepository

class SurveyViewModel(private val hotelRepository: HotelRepository) : ViewModel() {
    private var selectedSet: MutableSet<String> = mutableSetOf()

    init {
        getListSurvey()
    }

    fun getListSurvey() = hotelRepository.getInterestParameter()

    fun setSelectedSurvey(value: String) {
        var isSame = false
        if (selectedSet.isEmpty()) {
            selectedSet.add(value)
        } else {
            run breaker@{
                selectedSet.forEach { item ->
                    if (item == value) {
                        selectedSet.remove(value)
                        isSame = true
                        return@breaker
                    }
                }
            }
            if (!isSame) {
                selectedSet.add(value)
            }
        }
    }

    fun getSelectedSurvey() = selectedSet
}