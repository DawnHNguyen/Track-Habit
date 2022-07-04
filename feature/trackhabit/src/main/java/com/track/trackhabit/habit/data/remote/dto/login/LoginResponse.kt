package com.track.trackhabit.habit.data.remote.dto.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String
)
