package com.nexthotel_app.ui

import com.nexthotel_app.ui.main.home.HomeUseCase
import com.nexthotel_app.ui.main.home.HomeUseCaseImplement
import com.nexthotel_app.ui.main.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single<HomeUseCase> { HomeUseCaseImplement(get()) }
    viewModel { HomeViewModel(get()) }
}