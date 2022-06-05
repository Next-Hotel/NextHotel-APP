package com.nexthotel_app.ui.main.favorite

import androidx.lifecycle.MutableLiveData
import com.nexthotel_app.base.BaseViewModel
import com.nexthotel_app.data.local.hotel.HotelSchema
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(
    private val useCase: FavoriteUseCase,
) : BaseViewModel() {


    private val _state = MutableLiveData<ListFavoriteState>()

    val state get() = _state

    fun list() = useCase.list()
        .subscribeOn(Schedulers.io())
        .doOnSubscribe {
            _state.postValue(ListFavoriteState.OnLoading)
        }
        .subscribe({
            _state.postValue(ListFavoriteState.OnSuccess(it))
        }, {
            _state.postValue(ListFavoriteState.OnError(it.message ?: ""))
        })
        .disposeOnCleared()

    fun deleteAll() = useCase.truncate()
        .subscribeOn(Schedulers.io())
        .doOnSubscribe {
            _state.postValue(ListFavoriteState.OnLoading)
        }
        .subscribe({
            useCase.list()
        }, {
            _state.postValue(ListFavoriteState.OnError(it.message ?: ""))
        })
        .disposeOnCleared()


}

sealed class ListFavoriteState {
    object OnLoading : ListFavoriteState()
    data class OnSuccess(val list: List<HotelSchema>) : ListFavoriteState()
    data class OnError(val message: String) : ListFavoriteState()
}