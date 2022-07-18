package com.track.trackhabit.auth.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class VerifyEmailTokenRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String
)
