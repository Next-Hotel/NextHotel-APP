package com.nexthotel_app.core.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hotel(
    val id: String,
    val namaHotel: String,
    val rating: String,
    val kota: String,
    val priceRange: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String,
) : Parcelable