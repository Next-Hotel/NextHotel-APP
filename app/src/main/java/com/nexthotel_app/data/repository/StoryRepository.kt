package com.nexthotel_app.data.repository

import HotelDatabase
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nexthotel_app.data.remote.HotelRemoteMediator
import com.nexthotel_app.data.remote.response.ListHotelItem
import com.nexthotel_app.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import wrapEspressoIdlingResource

class HotelRepository(
    private val hotelDatabase: HotelDatabase,
    private val apiService: ApiService,
) {
    fun getPagingHotels(token: String): Flow<PagingData<ListHotelItem>> {
        wrapEspressoIdlingResource {
            @OptIn(ExperimentalPagingApi::class)
            return Pager(
                config = PagingConfig(
                    pageSize = 5
                ),
                remoteMediator = HotelRemoteMediator(hotelDatabase, apiService),
                pagingSourceFactory = {
                    hotelDatabase.hotelDao().getHotel()
                }
            ).flow
        }
    }

    companion object {
        private const val TAG = "HotelRepository"
    }
}