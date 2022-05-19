package com.nexthotel_app.core.source.remote.response

import com.nexthotel_app.core.source.model.Hotel

data class HotelsResponse(
    val error: String,
    val message: String,
    val listHotel: List<Hotel>,
)