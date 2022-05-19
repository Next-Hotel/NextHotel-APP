package com.nexthotel_app.core.source.datasource

import com.nexthotel_app.core.source.remote.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

}