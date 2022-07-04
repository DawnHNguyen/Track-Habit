package com.track.trackhabit.habit.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("fullname")
    val fullname: String
)
