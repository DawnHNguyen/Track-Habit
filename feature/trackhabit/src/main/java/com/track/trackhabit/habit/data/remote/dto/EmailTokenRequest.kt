package com.track.trackhabit.habit.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmailTokenRequest(
    @SerializedName("email")
    val email: String
)
