package com.gonexwind.nexthotel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gonexwind.nexthotel.model.Hotel
import com.gonexwind.nexthotel.model.HotelsResponse
import com.gonexwind.nexthotel.repository.SearchRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    app: Application,
    private val searchRepository: SearchRepository
) : AndroidViewModel(app) {

    // contains the result of search, online and offline
    var searchResult = MutableLiveData<List<Hotel>>()

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
        //using the viewModelScope to call this in UI thread, the viewModelScope gets cancelled by itself
        viewModelScope.launch {
            if (searchViewValue.isNotEmpty())
                searchView.value = searchViewValue
            viewState.value = SearchViewState.LOADING.ordinal
            searchCall(searchViewValue)
        }
    }

    /** this call is happening in the background thread */
    private suspend fun searchCall(searchText: String) {
        val response = searchRepository.search(searchText)
        manageViewState(response)
    }

    /** clear the viewState and the searchView when clicking on the close button */
    fun clearSearchResult() {
        searchResult.value = emptyList()
        searchView.value = String()
        viewState.value = SearchViewState.INITIAL_SCREEN.ordinal
    }

    /** update the view state depending on the network call response */
    private fun manageViewState(response: Response<HotelsResponse>) {
        if (response.isSuccessful) {
            searchResult.value = response.body()?.data
            if (searchResult.value?.isEmpty() == true) {
                viewState.value = SearchViewState.RESULT_NOT_FOUND.ordinal
            } else {
                viewState.value = SearchViewState.SEARCH_RESULT.ordinal
            }
        } else {
            viewState.value = SearchViewState.ERROR.ordinal
        }
    }

    // using this enum to keep track of the state view
    enum class SearchViewState {
        INITIAL_SCREEN, LOADING, SEARCH_RESULT, ERROR, RESULT_NOT_FOUND
    }

}