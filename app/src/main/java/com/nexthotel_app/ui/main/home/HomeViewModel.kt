package com.nexthotel_app.ui.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import com.nexthotel_app.base.BaseViewModel
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val useCase: HomeUseCase,
) : BaseViewModel() {
    private val _stateList = MutableLiveData<HomeState>()

    val stateList get() = _stateList

    fun refresh() {
        getPagingHotel()
    }

    private fun getPagingHotel() {
        getPagingData()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                _stateList.postValue(HomeState.OnLoading)
            }.subscribe({
                _stateList.postValue(HomeState.OnSuccess(it))
            }, {
                _stateList.postValue(HomeState.OnError(it?.message ?: "Terjadi Kesalahan"))
            }).disposeOnCleared()
    }

    private fun getPagingData(): Flowable<PagingData<HotelSchema>> {
        return useCase.pagingData()
            .map { pagingData ->
                pagingData.filter {
                    it.imageUrl != null
                }
            }.cachedIn(viewModelScope)

    }
}

sealed class HomeState {
    object OnLoading : HomeState()
    data class OnSuccess(val pagingHotel: PagingData<HotelSchema>) : HomeState()
    data class OnError(val message: String) : HomeState()
}