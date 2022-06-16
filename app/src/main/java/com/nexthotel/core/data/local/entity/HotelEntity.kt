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
    var isBookmarked: Boolean,
) : Parcelable
