package com.track.trackhabit.habit.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CreateInspectionRequest(
    @SerializedName("habitId")
    val habitId: Int,
    @SerializedName("isChecked")
    val isChecked: Boolean,
    @SerializedName("time")
    val time: String
)