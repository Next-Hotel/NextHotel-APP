package com.nexthotel_app.data.local.hotel.remote_keys_entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysSchema(
    @PrimaryKey val id: Int?,
    val prevKey: Int?,
    val nextKey: Int?
)