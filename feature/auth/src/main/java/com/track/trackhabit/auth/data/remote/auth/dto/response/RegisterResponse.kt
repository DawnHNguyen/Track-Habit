package com.track.trackhabit.auth.data.remote.auth.dto.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    val message: String?
)
