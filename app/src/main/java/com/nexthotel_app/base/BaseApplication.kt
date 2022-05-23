package com.nexthotel_app.base

import android.app.Application
import com.nexthotel_app.BuildConfig
import com.nexthotel_app.data.local.localFavModule
import com.nexthotel_app.data.local.localModule
import com.nexthotel_app.data.remote.errorHandleModule
import com.nexthotel_app.data.remote.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class BaseApplication : Application() {

    abstract fun defineDependencies(): List<Module>

    override fun onCreate() {
        super.onCreate()
        dependenciesInjection()
    }

    private fun dependenciesInjection() {
        startKoin {

            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)

            androidContext(this@BaseApplication)

            modules(
                mutableListOf(
                    remoteModule,
                    localModule,
                    localFavModule,
                    errorHandleModule
                )
                    .apply { addAll(defineDependencies()) }
            )
        }
    }
}