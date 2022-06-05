package com.nexthotel_app.repository.hotel

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelDatabase
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import com.nexthotel_app.data.local.hotel.remote_keys_entity.RemoteKeysSchema
import com.nexthotel_app.data.remote.response.HotelResponse
import com.nexthotel_app.data.remote.service.HotelServices
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class HotelRemoteMediator(
    private val service: HotelServices,
    private val database: HotelDatabase,
) : RxRemoteMediator<Int, HotelSchema>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, HotelSchema>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")
                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    service.hotels(
                        page = page,
                        size = state.config.pageSize
                    )
                        .map { insertToDb(page, loadType, it) }
                        .map<MediatorResult> {
                            MediatorResult.Success(endOfPaginationReached = it.listHotel.isEmpty())
                        }
                        .onErrorReturn {
                            MediatorResult.Error(it)
                        }
                }
            }
            .onErrorReturn { MediatorResult.Error(it) }

    }

    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: HotelResponse): HotelResponse {
        database.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                database.remoteKeysDao().deleteRemoteKeys()
                database.hotel().deleteAll()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.listHotel.isEmpty()) null else page + 1
            val keys = data.listHotel.map {
                RemoteKeysSchema(id = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            database.remoteKeysDao().insertAll(keys)
            database.hotel().insertHotel(data.listHotel)
            database.setTransactionSuccessful()

        } finally {
            database.endTransaction()
        }

        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, HotelSchema>): RemoteKeysSchema? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.remoteKeysDao().getRemoteKeysId(repo.id)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, HotelSchema>): RemoteKeysSchema? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { hotel ->
            database.remoteKeysDao().getRemoteKeysId(hotel.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, HotelSchema>): RemoteKeysSchema? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }
}
