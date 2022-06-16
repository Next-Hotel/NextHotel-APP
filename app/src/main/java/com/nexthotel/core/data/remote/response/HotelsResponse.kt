package com.nexthotel.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class HotelsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Hotel>,
)

data class Hotel(
    @SerializedName("id") val id: Int,
    @SerializedName("hotel") val name: String,
    @SerializedName("kota") val city: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("rating") val rate: Double,
    @SerializedName("stars") val stars: Int,
    @SerializedName("description") val description: String,
    @SerializedName("harga") val priceRange: String,
    @SerializedName("reviews") val reviews: String,
    @SerializedName("accessibilty_list") val accessibiltyList: String?,
    @SerializedName("places_nearby") val placesNearby: String?,
    @SerializedName("sports_and_recreations_list") val sportsAndRecreationsList: String?,
    @SerializedName("transportation_list") val transportationList: String?,
    @SerializedName("business_facilities_list") val businessFacilitiesList: String?,
    @SerializedName("public_facilities_list") val publicFacilitiesList: String?,
    @SerializedName("kids_and_pets_list") val kidsAndPetsList: String?,
    @SerializedName("food_and_drinks_list") val foodAndDrinksList: String?,
    @SerializedName("shuttle_service_list") val shuttleServiceList: String?,
    @SerializedName("nearby_facilities_list") val nearbyFacilitiesList: String?,
    @SerializedName("general_list") val generalList: String?,
    @SerializedName("connectivity_list") val connectivityList: String?,
    @SerializedName("in-room_facilities_list") val inRoomFacilitiesList: String?,
    @SerializedName("hotel_services_list") val hotelServicesList: String?,
    @SerializedName("things_to_do_list") val thingsToDoList: String?,
    @SerializedName("Score") val score: Double,
)
