package com.nexthotel_app.ui.main.explore

import androidx.lifecycle.MutableLiveData
import com.nexthotel_app.base.BaseViewModel
import com.nexthotel_app.data.local.hotel.HotelSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ExploreViewModel(private val useCase: ExploreUseCase) : BaseViewModel() {

    private val _stateList = MutableLiveData<ExploreState>()

    val stateList get() = _stateList

    private val subscribeSearch: PublishSubject<String> = PublishSubject.create()

    init {

        subscribeSearch
            .debounce(800, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getSearch(it) }.disposeOnCleared()
    }

    fun refresh() {

        subscribeSearch
            .debounce(800, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getSearch(it) }.disposeOnCleared()
    }

    fun search(keyword: String) {
        subscribeSearch.onNext(keyword)
    }


    private fun getSearch(keyword: String) {
        useCase.search(keyword)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                _stateList.postValue(ExploreState.OnLoading)
            }.subscribe({
                _stateList.postValue(ExploreState.OnSuccess(it))
            }, {
                _stateList.postValue(ExploreState.OnError(it?.message ?: "Terjadi Kesalahan"))
            }).disposeOnCleared()
    }

}

sealed class ExploreState {
    object OnLoading : ExploreState()
    data class OnSuccess(val list: List<HotelSchema>) : ExploreState()
    data class OnError(val message: String) : ExploreState()
}