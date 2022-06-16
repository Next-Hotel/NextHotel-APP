package com.nexthotel.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class InterestResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<String>,
)