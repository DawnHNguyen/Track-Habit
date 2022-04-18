package com.track.trackhabit.habit.domain.entity.local

import androidx.room.Embedded
import androidx.room.Relation

data class HabitOwner(
    @Embedded val userLocal: UserLocal,
    @Relation(
        parentColumn = "userId",
        entityColumn = "habitId"
    )
    val habitList: List<HabitLocal>
)