package com.nexthotel_app.ui.main.explore

import com.nexthotel_app.data.local.hotel.HotelSchema
import com.nexthotel_app.data.remote.response.mapToList
import com.nexthotel_app.repository.hotel.HotelRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ExploreUseCaseImplement(private val repository: HotelRepository) : ExploreUseCase {

    override fun search(keyword: String): Observable<List<HotelSchema>> {
        return repository.hotelsSearch(keyword)
            .subscribeOn(Schedulers.io())
            .flatMap { response -> response.mapToList() }
    }

}