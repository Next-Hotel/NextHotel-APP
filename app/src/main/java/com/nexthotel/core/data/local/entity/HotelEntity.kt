package com.nexthotel.core.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "hotel")
@Parcelize
data class HotelEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val city: String,
    val imageUrl: String,
    val rate: String,
    val description: String,
    val priceRange: String,
    val stars: String,
    val reviews: String,
    val accessibiltyList: String?,
    val placesNearby: String?,
    val sportsAndRecreationsList: String?,
    val transportationList: String?,
    val businessFacilitiesList: String?,
    val publicFacilitiesList: String?,
    val kidsAndPetsList: String?,
    val foodAndDrinksList: String?,
    val shuttleServiceList: String?,
    val nearbyFacilitiesList: String?,
    val generalList: String?,
    val connectivityList: String?,
    val inRoomFacilitiesList: String?,
    val hotelServicesList: String?,
    val thingsToDoList: String?,
    val score: Double,
    var isBookmarked: Boolean,
) : Parcelable