package com.track.trackhabit.habit.data.remote.dto.response


import com.google.gson.annotations.SerializedName

data class AddHabitResponse(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("reminderDays")
    val reminderDays: List<Int>,
    @SerializedName("reminderTime")
    val reminderTime: String,
    @SerializedName("title")
    val title: String
)