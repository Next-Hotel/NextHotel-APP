package com.nexthotel_app.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()
    private var _token: String = ""

    val token get() = _token

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    protected fun Disposable.disposeOnCleared(): Disposable {
        disposables.add(this)
        return this
    }

    fun setToken(token: String) {
        _token = token
    }
}