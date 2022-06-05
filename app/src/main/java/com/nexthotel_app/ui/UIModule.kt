package com.nexthotel_app.ui

import com.nexthotel_app.ui.main.detail.DetailUseCase
import com.nexthotel_app.ui.main.detail.DetailUseCaseImpl
import com.nexthotel_app.ui.main.detail.DetailViewModel
import com.nexthotel_app.ui.main.explore.ExploreUseCase
import com.nexthotel_app.ui.main.explore.ExploreUseCaseImplement
import com.nexthotel_app.ui.main.explore.ExploreViewModel
import com.nexthotel_app.ui.main.favorite.FavoriteUseCase
import com.nexthotel_app.ui.main.favorite.FavoriteUseCaseImpl
import com.nexthotel_app.ui.main.favorite.FavoriteViewModel
import com.nexthotel_app.ui.main.home.HomeUseCase
import com.nexthotel_app.ui.main.home.HomeUseCaseImplement
import com.nexthotel_app.ui.main.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single<HomeUseCase> { HomeUseCaseImplement(get()) }
    viewModel { HomeViewModel(get()) }

    single<ExploreUseCase> { ExploreUseCaseImplement(get()) }
    viewModel { ExploreViewModel(get()) }

    single<DetailUseCase> { DetailUseCaseImpl(get()) }
    viewModel { DetailViewModel(get()) }

    single<FavoriteUseCase> { FavoriteUseCaseImpl(get()) }
    viewModel { FavoriteViewModel(get()) }
}