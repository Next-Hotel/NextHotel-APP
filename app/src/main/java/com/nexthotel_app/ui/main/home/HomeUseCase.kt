package com.nexthotel_app.ui.main.home

import androidx.paging.PagingData
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import io.reactivex.Flowable

interface HomeUseCase {
    fun pagingData(): Flowable<PagingData<HotelSchema>>
}