package com.track.trackhabit.habit.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VerifyEmailTokenRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String
)
