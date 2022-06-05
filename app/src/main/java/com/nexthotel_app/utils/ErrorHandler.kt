package com.nexthotel_app.utils

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nexthotel_app.R
import com.nexthotel_app.data.remote.response.ErrorResponse
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RxErrorHandler(private val application: Application) : Consumer<Throwable> {

    private val errorMessageToDisplay = PublishSubject.create<String>()

    init {
        RxJavaPlugins.setErrorHandler(this)
    }


    override fun accept(t: Throwable) {
        when (val cause = parseCause(t)) {
            is SocketTimeoutException, is ConnectException, is UnknownHostException, is SocketException -> {
                Timber.w("Network fail: $cause")
                errorMessageToDisplay.onNext(application.getString(R.string.error_network_fail))
            }
            is HttpException -> {
                val errorBody = cause.response()?.errorBody()
                val gson = Gson()
                val type = object : TypeToken<ErrorResponse>() {}.type
                val errorResponse: ErrorResponse? = gson.fromJson(errorBody!!.charStream(), type)
                Timber.e(errorResponse?.status)

                errorMessageToDisplay.onNext("${errorResponse?.status}")
            }
            else -> {
                Timber.e(cause)
                throw cause
            }
        }
    }

    private fun parseCause(t: Throwable): Throwable {
        when (t) {
            is OnErrorNotImplementedException, is UndeliverableException, is RuntimeException -> {
                t.cause?.run { return this } ?: throw ParseCauseFailException(t)
            }
        }
        return t
    }
}

class ParseCauseFailException(t: Throwable) : RuntimeException(t)