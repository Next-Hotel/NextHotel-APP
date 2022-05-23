package com.nexthotel_app.data.local.hotel.hotel_fav_entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface HotelFavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(data: HotelFavSchema): Completable

    @Query("SELECT * FROM ${HotelFavLocalConfig.TABLE_FAVORITE}")
    fun findAll(): Flowable<List<HotelFavSchema>>

    @Query("SELECT * FROM ${HotelFavLocalConfig.TABLE_FAVORITE} WHERE namaHotel = :namaHotel LIMIT 1")
    fun find(namaHotel: String): Flowable<HotelFavSchema>

    @Query("DELETE FROM ${HotelFavLocalConfig.TABLE_FAVORITE} WHERE namaHotel = :namaHotel")
    fun delete(namaHotel: String): Completable

    @Query("DELETE FROM ${HotelFavLocalConfig.TABLE_FAVORITE}")
    fun truncate(): Completable

}