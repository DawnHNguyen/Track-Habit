package com.track.trackhabit.habit.domain.entity

enum class SleepDuration(
    val sleepDurationHour: Int,
    val sleepDurationMin: Int,
    val sleepDuration: Double,
    val loop: Int
) {
    Six(9, 0, 9.0, 6),
    Five(7, 30, 7.5, 5),
    Four(6, 0, 6.0, 4),
    Three(4, 30, 4.5, 3)
}