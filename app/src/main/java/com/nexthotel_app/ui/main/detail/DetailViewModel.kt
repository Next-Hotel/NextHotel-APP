package com.nexthotel_app.ui.main.detail

import androidx.lifecycle.MutableLiveData
import com.nexthotel_app.base.BaseViewModel
import com.nexthotel_app.data.local.hotel.HotelSchema
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val useCase: DetailUseCase) : BaseViewModel()  {
    private val _state = MutableLiveData<FavoriteState>()
    val state get() = _state


    fun favorite(hotel: HotelSchema) {
        useCase.favorite(hotel)
            .subscribeOn(Schedulers.io())
            .subscribe({
                _state.postValue(FavoriteState.OnSaved)
            }, {
                _state.postValue(FavoriteState.OnError(it.message ?: ""))
            })
            .disposeOnCleared()
    }

    fun find(id: String) {
        useCase.find(id)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                state.postValue(FavoriteState.NotFound)
            }
            .subscribe({
                state.postValue(FavoriteState.OnSaved)
            }, {
                state.postValue(FavoriteState.OnError(it.message ?: ""))
            })
            .disposeOnCleared()
    }

    fun delete(id: String) {
        useCase.delete(id)
            .subscribeOn(Schedulers.io())
            .subscribe({
                _state.postValue(FavoriteState.NotFound)
            }, {
                _state.postValue(FavoriteState.OnError(it.message ?: ""))
            })
            .disposeOnCleared()
    }
}




sealed class FavoriteState {
    object NotFound : FavoriteState()
    object OnSaved : FavoriteState()
    data class OnError(val message: String) : FavoriteState()
}