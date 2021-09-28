package com.track.trackhabit.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val habits: List<Habit>)