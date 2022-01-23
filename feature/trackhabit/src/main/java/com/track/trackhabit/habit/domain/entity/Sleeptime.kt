package com.track.trackhabit.habit.domain.entity

data class Sleeptime(
    val sleepTimeId: Int,
    val sleepTime: String,
    val sleepDuration: Double,
    val loop: Int,
    val isClicked: Boolean = false
)