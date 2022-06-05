package com.nexthotel_app.ui.main.home

import androidx.paging.PagingData
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import com.nexthotel_app.repository.hotel.HotelRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class HomeUseCaseImplement(private val repository: HotelRepository) : HomeUseCase {
    override fun pagingData(): Flowable<PagingData<HotelSchema>> =
        repository.getHotel().subscribeOn(
            Schedulers.io()
        )
}