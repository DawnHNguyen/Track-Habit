package com.track.trackhabit.habit.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class EmailTokenRequest(
    @SerializedName("email")
    val email: String
)
