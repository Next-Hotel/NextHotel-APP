package com.nexthotel_app.ui

import com.nexthotel_app.ui.main.detail.DetailUseCase
import com.nexthotel_app.ui.main.detail.DetailUseCaseImplement
import com.nexthotel_app.ui.main.detail.DetailViewModel
import com.nexthotel_app.ui.main.explore.ExploreUseCase
import com.nexthotel_app.ui.main.explore.ExploreUseCaseImplement
import com.nexthotel_app.ui.main.explore.ExploreViewModel
import com.nexthotel_app.ui.main.favorite.FavoriteUseCase
import com.nexthotel_app.ui.main.favorite.FavoriteUseCaseImplement
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

    single<FavoriteUseCase> { FavoriteUseCaseImplement(get()) }
    viewModel { FavoriteViewModel(get()) }

    single<DetailUseCase> { DetailUseCaseImplement(get()) }
    viewModel { DetailViewModel(get()) }
}