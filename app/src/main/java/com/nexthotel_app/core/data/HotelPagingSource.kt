package com.nexthotel_app.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nexthotel_app.core.source.model.Hotel
import com.nexthotel_app.core.source.remote.network.ApiService

class HotelPagingSource(private val apiService: ApiService) :
    PagingSource<Int, Hotel>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Hotel>): Int? = state.anchorPosition?.let {
        val anchorPage = state.closestPageToPosition(it)
        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hotel> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getHotels(position, params.loadSize)

            LoadResult.Page(
                data = responseData.listHotel,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.listHotel.isNullOrEmpty()) null else position + 1,
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}