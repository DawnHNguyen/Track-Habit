package com.track.trackhabit.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    val time: Date,
    val performances: List<Inspection>
    )
