package com.nexthotel_app.data.local.hotel.hotel_entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nexthotel_app.data.local.hotel.HotelLocalConfig
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface HotelFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(data: HotelFavoriteSchema): Completable

    @Query("SELECT * FROM ${HotelLocalConfig.TABLE_FAVORITE_HOTEL}")
    fun findAll(): Flowable<List<HotelFavoriteSchema>>

    @Query("SELECT * FROM ${HotelLocalConfig.TABLE_FAVORITE_HOTEL} WHERE id = :id LIMIT 1")
    fun find(id: String): Flowable<HotelFavoriteSchema>

    @Query("DELETE FROM ${HotelLocalConfig.TABLE_FAVORITE_HOTEL} WHERE id = :id")
    fun delete(id: String): Completable

    @Query("DELETE FROM ${HotelLocalConfig.TABLE_FAVORITE_HOTEL}")
    fun truncate(): Completable
}