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
    // contains the result of search, online and offline
    var searchResult = MutableLiveData<List<HotelEntity>>()

    //contains the value of the searchView
    private var searchView = MutableLiveData<String>()


    // contains the state of the whole view, ex: loading, show result, etc
    var viewState = MutableLiveData<Int>()

    init {
        //showing the initial screen
        viewState.value = SearchViewState.INITIAL_SCREEN.ordinal

        // searchView is empty in the begin
        searchView.value = String()
    }

    fun searchForTasks(searchViewValue: String) {
        viewModelScope.launch {
            if (searchViewValue.isNotEmpty())
                searchView.value = searchViewValue
            viewState.value = SearchViewState.LOADING.ordinal
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
        viewState.value = SearchViewState.INITIAL_SCREEN.ordinal
    }

    private suspend fun manageViewState(response: Response<HotelsResponse>) {
        if (response.isSuccessful) {
            val hotels = response.body()?.data
            if (hotels != null) {
                searchResult.value = hotelRepository.searchResult(hotels)
            }

            if (searchResult.value?.isEmpty() == true) {
                viewState.value = SearchViewState.RESULT_NOT_FOUND.ordinal
            } else {
                viewState.value = SearchViewState.SEARCH_RESULT.ordinal
            }
        } else {
            viewState.value = SearchViewState.ERROR.ordinal
        }
    }

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

    enum class SearchViewState {
        INITIAL_SCREEN, LOADING, SEARCH_RESULT, ERROR, RESULT_NOT_FOUND
    }
}