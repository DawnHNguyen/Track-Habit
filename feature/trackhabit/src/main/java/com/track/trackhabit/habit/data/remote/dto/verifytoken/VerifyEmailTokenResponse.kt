package com.track.trackhabit.habit.data.remote.dto.verifytoken

import com.google.gson.annotations.SerializedName

data class VerifyEmailTokenResponse(
    @SerializedName("message")
    val message: String
)
