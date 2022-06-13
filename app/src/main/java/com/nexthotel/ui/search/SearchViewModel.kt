package com.nexthotel.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexthotel.core.data.HotelRepository
import com.nexthotel.core.data.local.entity.HotelEntity
import com.nexthotel.core.data.remote.response.HotelsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val hotelRepository: HotelRepository) : ViewModel() {
    var searchResult = MutableLiveData<List<HotelEntity>>()
    private var searchView = MutableLiveData<String>()
    var viewState = MutableLiveData<Int>()

    init {
        viewState.value = State.INITIAL_SCREEN.ordinal
        searchView.value = String()
    }

    fun searchForTasks(searchViewValue: String) {
        viewModelScope.launch {
            if (searchViewValue.isNotEmpty())
                searchView.value = searchViewValue
            viewState.value = State.LOADING.ordinal
            searchCall(searchViewValue)
        }
    }

    private suspend fun searchCall(searchText: String) {
        val response = hotelRepository.search(searchText)
        manageViewState(response)
    }

    fun clearSearchResult() {
        searchResult.value = emptyList()
        searchView.value = String()
        viewState.value = State.INITIAL_SCREEN.ordinal
    }

    private suspend fun manageViewState(response: Response<HotelsResponse>) =
        if (response.isSuccessful) {
            val hotels = response.body()?.data
            if (hotels != null) {
                searchResult.value = hotelRepository.searchResult(hotels)
            }

            if (searchResult.value?.isEmpty() == true) {
                viewState.value = State.RESULT_NOT_FOUND.ordinal
            } else {
                viewState.value = State.SEARCH_RESULT.ordinal
            }
        } else {
            viewState.value = State.ERROR.ordinal
        }

    fun saveHotel(hotel: HotelEntity) {
        viewModelScope.launch { hotelRepository.setBookmarkedHotel(hotel, true) }
    }

    fun deleteHotel(hotel: HotelEntity) {
        viewModelScope.launch { hotelRepository.setBookmarkedHotel(hotel, false) }
    }

    enum class State {
        INITIAL_SCREEN, LOADING, SEARCH_RESULT, ERROR, RESULT_NOT_FOUND
    }
}