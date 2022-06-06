package com.nexthotel.repository

import com.nexthotel.api.ApiConfig

class SearchRepository() {

    suspend fun search(query: String) = ApiConfig.getApiService().searchHotel(query)

}