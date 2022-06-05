package com.nexthotel_app.data.local.hotel.remote_keys_entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nexthotel_app.data.local.hotel.HotelLocalConfig

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKeySchema: List<RemoteKeysSchema>)

    @Query("SELECT * FROM ${HotelLocalConfig.TABLE_REMOTE_KEYS} WHERE id = :id")
    fun getRemoteKeysId(id: Int?): RemoteKeysSchema?

    @Query("DELETE FROM ${HotelLocalConfig.TABLE_REMOTE_KEYS}")
    fun deleteRemoteKeys()
}