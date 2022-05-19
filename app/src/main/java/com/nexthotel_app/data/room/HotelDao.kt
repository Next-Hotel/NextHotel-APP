package com.nexthotel_app.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nexthotel_app.data.remote.response.ListHotelItem

@Dao
interface HotelDao {
    @Query("SELECT * FROM hotel")
    fun getHotel(): PagingSource<Int, ListHotelItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotel(storyEntity: List<ListHotelItem>)

    @Query("DELETE FROM hotel")
    suspend fun deleteAll()
}