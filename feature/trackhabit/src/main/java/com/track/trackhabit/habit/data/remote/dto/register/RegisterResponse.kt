package com.track.trackhabit.habit.data.remote.dto.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    val message: String
)
