package com.nexthotel_app.ui.main.explore

import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import io.reactivex.Observable

interface ExploreUseCase {

    fun search(keyword: String): Observable<List<HotelSchema>>

}
