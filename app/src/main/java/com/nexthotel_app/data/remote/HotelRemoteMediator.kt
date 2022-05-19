package com.nexthotel_app.data.remote

import HotelDatabase
import RemoteKeys
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nexthotel_app.data.remote.response.ListHotelItem
import com.nexthotel_app.data.remote.retrofit.ApiService

@OptIn(ExperimentalPagingApi::class)
class HotelRemoteMediator(
    private val database: HotelDatabase,
    private val apiService: ApiService,
) : RemoteMediator<Int, ListHotelItem>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListHotelItem>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val responseData =
                apiService.getHotels(page, state.config.pageSize).listHotel
            val endOfPaginationReached = responseData.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.hotelDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = responseData.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                database.remoteKeysDao().insertAll(keys)
                database.hotelDao().insertHotel(responseData)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ListHotelItem>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            database.remoteKeysDao().getRemoteKeysId(it.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ListHotelItem>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            database.remoteKeysDao().getRemoteKeysId(it.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ListHotelItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                database.remoteKeysDao().getRemoteKeysId(it)
            }
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}