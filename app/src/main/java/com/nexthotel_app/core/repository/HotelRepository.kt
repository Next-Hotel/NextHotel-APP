package com.nexthotel_app.core.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.nexthotel_app.core.data.HotelPagingSource
import com.nexthotel_app.core.source.datasource.RemoteDataSource
import com.nexthotel_app.core.source.model.Hotel
import com.nexthotel_app.core.source.remote.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HotelRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val apiService: ApiService
) {
    fun getHotelsBestForYou(): LiveData<PagingData<Hotel>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = { HotelPagingSource(apiService) }
    ).liveData

    fun getHotelsSpecialOffers(): LiveData<PagingData<Hotel>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = { HotelPagingSource(apiService) }
    ).liveData

}