package com.nexthotel.core.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.nexthotel.core.data.local.entity.HotelEntity
import com.nexthotel.core.data.local.room.HotelDao
import com.nexthotel.core.data.remote.network.ApiService
import com.nexthotel.core.data.remote.response.Hotel

class HotelRepository private constructor(
    private val apiService: ApiService,
    private val hotelDao: HotelDao,
) {

    fun getBestPick(): LiveData<Result<List<HotelEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getBestPick()
            val hotels = response.data
            val hotelList = hotels.map {
                val isBookmarked = hotelDao.isHotelBookmarked(it.id)
                HotelEntity(
                    id = it.id,
                    name = it.name,
                    city = it.city,
                    imageUrl = it.imageUrl,
                    rate = it.rate.toString() + " / 10",
                    description = it.description,
                    priceRange = "Rp. " + it.priceRange,
                    isBookmarked = isBookmarked,
                    stars = it.stars.toString(),
                    reviews = it.reviews,
                    accessibiltyList = it.accessibiltyList,
                    placesNearby = it.placesNearby,
                    sportsAndRecreationsList = it.sportsAndRecreationsList,
                    transportationList = it.transportationList,
                    businessFacilitiesList = it.businessFacilitiesList,
                    publicFacilitiesList = it.publicFacilitiesList,
                    kidsAndPetsList = it.kidsAndPetsList,
                    foodAndDrinksList = it.foodAndDrinksList,
                    shuttleServiceList = it.shuttleServiceList,
                    nearbyFacilitiesList = it.nearbyFacilitiesList,
                    generalList = it.generalList,
                    connectivityList = it.connectivityList,
                    inRoomFacilitiesList = it.inRoomFacilitiesList,
                    hotelServicesList = it.hotelServicesList,
                    thingsToDoList = it.thingsToDoList,
                    score = it.score,
                )
            }
            hotelDao.deleteAll()
            hotelDao.insertHotel(hotelList)
        } catch (e: Exception) {
            Log.d("HotelRepository", "getBestPick: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

        val localData: LiveData<Result<List<HotelEntity>>> =
            hotelDao.getBestPick().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getHotelForYou(): LiveData<Result<List<HotelEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getHotelForYou()
            val hotels = response.data
            val hotelList = hotels.map {
                val isBookmarked = hotelDao.isHotelBookmarked(it.id)
                HotelEntity(
                    id = it.id,
                    name = it.name,
                    city = it.city,
                    imageUrl = it.imageUrl,
                    rate = it.rate.toString() + " / 10",
                    description = it.description,
                    priceRange = "Rp. " + it.priceRange,
                    isBookmarked = isBookmarked,
                    stars = it.stars.toString(),
                    reviews = it.reviews,
                    accessibiltyList = it.accessibiltyList,
                    placesNearby = it.placesNearby,
                    sportsAndRecreationsList = it.sportsAndRecreationsList,
                    transportationList = it.transportationList,
                    businessFacilitiesList = it.businessFacilitiesList,
                    publicFacilitiesList = it.publicFacilitiesList,
                    kidsAndPetsList = it.kidsAndPetsList,
                    foodAndDrinksList = it.foodAndDrinksList,
                    shuttleServiceList = it.shuttleServiceList,
                    nearbyFacilitiesList = it.nearbyFacilitiesList,
                    generalList = it.generalList,
                    connectivityList = it.connectivityList,
                    inRoomFacilitiesList = it.inRoomFacilitiesList,
                    hotelServicesList = it.hotelServicesList,
                    thingsToDoList = it.thingsToDoList,
                    score = it.score,
                )
            }
            hotelDao.deleteAll()
            hotelDao.insertHotel(hotelList)
        } catch (e: Exception) {
            Log.d("HotelRepository", "getHotelForYou: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

        val localData: LiveData<Result<List<HotelEntity>>> =
            hotelDao.getHotelForYou().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getExplore(): LiveData<Result<List<HotelEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getExploreHotel()
            val hotels = response.data
            val hotelList = hotels.map {
                val isBookmarked = hotelDao.isHotelBookmarked(it.id)
                HotelEntity(
                    id = it.id,
                    name = it.name,
                    city = it.city,
                    imageUrl = it.imageUrl,
                    rate = it.rate.toString() + " / 10",
                    description = it.description,
                    priceRange = "Rp. " + it.priceRange,
                    isBookmarked = isBookmarked,
                    stars = it.stars.toString(),
                    reviews = it.reviews,
                    accessibiltyList = it.accessibiltyList,
                    placesNearby = it.placesNearby,
                    sportsAndRecreationsList = it.sportsAndRecreationsList,
                    transportationList = it.transportationList,
                    businessFacilitiesList = it.businessFacilitiesList,
                    publicFacilitiesList = it.publicFacilitiesList,
                    kidsAndPetsList = it.kidsAndPetsList,
                    foodAndDrinksList = it.foodAndDrinksList,
                    shuttleServiceList = it.shuttleServiceList,
                    nearbyFacilitiesList = it.nearbyFacilitiesList,
                    generalList = it.generalList,
                    connectivityList = it.connectivityList,
                    inRoomFacilitiesList = it.inRoomFacilitiesList,
                    hotelServicesList = it.hotelServicesList,
                    thingsToDoList = it.thingsToDoList,
                    score = it.score,
                )
            }
            hotelDao.deleteAll()
            hotelDao.insertHotel(hotelList)
        } catch (e: Exception) {
            Log.d("HotelRepository", "getExplore: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

        val localData: LiveData<Result<List<HotelEntity>>> =
            hotelDao.getExplore().map { Result.Success(it) }
        emitSource(localData)
    }

    suspend fun search(query: String) = apiService.searchHotel(query)

    suspend fun searchResult(hotels: List<Hotel>): List<HotelEntity> {
        val hotelList = hotels.map {
            val isBookmarked = hotelDao.isHotelBookmarked(it.id)
            HotelEntity(
                id = it.id,
                name = it.name,
                city = it.city,
                imageUrl = it.imageUrl,
                rate = it.rate.toString() + " / 10",
                description = it.description,
                priceRange = "Rp. " + it.priceRange,
                isBookmarked = isBookmarked,
                stars = it.stars.toString(),
                reviews = it.reviews,
                accessibiltyList = it.accessibiltyList,
                placesNearby = it.placesNearby,
                sportsAndRecreationsList = it.sportsAndRecreationsList,
                transportationList = it.transportationList,
                businessFacilitiesList = it.businessFacilitiesList,
                publicFacilitiesList = it.publicFacilitiesList,
                kidsAndPetsList = it.kidsAndPetsList,
                foodAndDrinksList = it.foodAndDrinksList,
                shuttleServiceList = it.shuttleServiceList,
                nearbyFacilitiesList = it.nearbyFacilitiesList,
                generalList = it.generalList,
                connectivityList = it.connectivityList,
                inRoomFacilitiesList = it.inRoomFacilitiesList,
                hotelServicesList = it.hotelServicesList,
                thingsToDoList = it.thingsToDoList,
                score = it.score,
            )
        }
        return hotelList
    }

    fun getBookmarkedHotel(): LiveData<List<HotelEntity>> = hotelDao.getBookmarkedHotel()

    suspend fun setBookmarkedHotel(hotel: HotelEntity, isBookmarked: Boolean) {
        hotel.isBookmarked = isBookmarked
        hotelDao.updateHotel(hotel)
    }

    companion object {
        @Volatile
        private var instance: HotelRepository? = null

        fun getInstance(apiService: ApiService, hotelDao: HotelDao): HotelRepository {
            return instance ?: synchronized(this) {
                instance ?: HotelRepository(apiService, hotelDao)
            }.also { instance = it }
        }
    }
}