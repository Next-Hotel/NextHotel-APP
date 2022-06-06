package com.gonexwind.nexthotel.repository

import com.gonexwind.nexthotel.api.ApiConfig

class SearchRepository() {

    suspend fun search(query: String) = ApiConfig.getApiService().searchHotel(query)

}