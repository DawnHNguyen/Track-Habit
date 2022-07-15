package com.track.trackhabit.auth.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class EmailTokenRequest(
    @SerializedName("email")
    val email: String
)
