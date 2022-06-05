package com.nexthotel_app

import com.nexthotel_app.data.local.daoModule
import com.nexthotel_app.data.remote.serviceModule
import com.nexthotel_app.repository.repositoryModule
import com.nexthotel_app.ui.viewModelModule
import org.koin.core.module.Module

class MyApp : com.nexthotel_app.base.BaseApplication() {
    override fun defineDependencies(): List<Module> {
        return listOf(
            daoModule,
            serviceModule,
            repositoryModule,
            viewModelModule
        )
    }
}