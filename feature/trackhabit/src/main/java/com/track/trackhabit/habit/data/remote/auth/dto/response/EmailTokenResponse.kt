package com.track.trackhabit.habit.data.remote.auth.dto.response

import com.google.gson.annotations.SerializedName

data class EmailTokenResponse(
    @SerializedName("message")
    val message: String
)
