package com.nexthotel_app.repository.hotel

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelDatabase
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import com.nexthotel_app.data.remote.service.HotelServices
import io.reactivex.Flowable

class HotelRepository(
    private val dao: HotelDatabase, private val service: HotelServices
) {
    fun getHotel(): Flowable<PagingData<HotelSchema>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                maxSize = 20,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            remoteMediator = HotelRemoteMediator(
                service,
                dao,
            ),
            pagingSourceFactory = {
                dao.hotel().getHotels()
            }
        ).flowable
    }

    fun hotelsSearch(keyword: String) = service.hotelsSearch(keyword)

}