package com.track.trackhabit.habit.data.remote.dto


import com.google.gson.annotations.SerializedName

data class HabitRequest(
    @SerializedName("description")
    val description: String?,
    @SerializedName("reminderDays")
    val reminderDays: List<Int>,
    @SerializedName("reminderTime")
    val reminderTime: String,
    @SerializedName("title")
    val title: String
)