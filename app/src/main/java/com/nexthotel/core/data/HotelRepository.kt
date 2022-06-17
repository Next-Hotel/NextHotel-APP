package com.nexthotel.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.nexthotel.core.data.remote.network.ApiService
import com.nexthotel.core.data.remote.response.Hotel

class HotelRepository private constructor(
    private val remoteData: ApiService
) {
    fun getInterestParameter(): LiveData<Result<List<String>>> = liveData {
        emit(Result.Loading)
        try {
            val response = remoteData.getInterestParameter()
            emit(Result.Success(response.data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getHotelForYou(interest: Set<String>): LiveData<Result<List<Hotel>>> = liveData {
        emit(Result.Loading)
        try {
            val response = remoteData.getHotelForYou(interest)
            emit(Result.Success(response.data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getBestPick(): LiveData<Result<List<Hotel>>> = liveData {
        emit(Result.Loading)
        try {
            val response = remoteData.getBestPick()
            emit(Result.Success(response.data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getExplore(): LiveData<Result<List<Hotel>>> = liveData {
        emit(Result.Loading)
        try {
            val response = remoteData.getExploreHotel()
            emit(Result.Success(response.data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun search(query: String) = remoteData.searchHotel(query)

    fun searchResult(hotels: List<Hotel>) : List<Hotel> = hotels

    companion object {
        @Volatile
        private var instance: HotelRepository? = null

        fun getInstance(remoteData: ApiService): HotelRepository {
            return instance ?: synchronized(this) {
                instance ?: HotelRepository(remoteData)
            }.also { instance = it }
        }
    }
}