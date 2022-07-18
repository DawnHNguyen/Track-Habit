package com.track.trackhabit.auth.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("fullName")
    val fullName: String
)
