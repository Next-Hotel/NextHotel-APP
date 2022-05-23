package com.nexthotel_app.data.local.hotel.hotel_entity

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HotelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHotel(story: List<HotelSchema>)

    @Query("SELECT * FROM ${HotelLocalConfig.TABLE_HOTEL}")
    fun getHotels(): PagingSource<Int, HotelSchema>

    @Query("DELETE FROM ${HotelLocalConfig.TABLE_HOTEL}")
    fun deleteAll()
}