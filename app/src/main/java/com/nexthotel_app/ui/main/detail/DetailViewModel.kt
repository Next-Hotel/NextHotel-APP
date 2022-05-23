package com.nexthotel_app.ui.main.detail

import androidx.lifecycle.MutableLiveData
import com.nexthotel_app.base.BaseViewModel
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val method: DetailUseCase) : BaseViewModel() {

    private val _favoriteState = MutableLiveData<FavoriteState>()

    val favoriteState get() = _favoriteState


    fun favorite(hotel: HotelSchema) {
        method.favorite(hotel)
            .subscribeOn(Schedulers.io())
            .subscribe({
                _favoriteState.postValue(FavoriteState.OnSaved)
            }, {
                _favoriteState.postValue(FavoriteState.OnError(it.message ?: ""))
            })
            .disposeOnCleared()
    }

    fun find(username: String?) {
        if (username != null) {
            method.find(username)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    _favoriteState.postValue(FavoriteState.NotFound)
                }
                .subscribe({
                    _favoriteState.postValue(FavoriteState.OnSaved)
                }, {
                    _favoriteState.postValue(FavoriteState.OnError(it.message ?: ""))
                })
                .disposeOnCleared()
        }
    }

    fun delete(name: String?) {
        if (name != null) {
            method.delete(name)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _favoriteState.postValue(FavoriteState.NotFound)
                }, {
                    _favoriteState.postValue(FavoriteState.OnError(it.message ?: ""))
                })
                .disposeOnCleared()
        }
    }

}

sealed class FavoriteState {
    object NotFound : FavoriteState()
    object OnSaved : FavoriteState()
    data class OnError(val message: String) : FavoriteState()
}