package com.track.trackhabit.habit.domain.entity.local

import androidx.room.Embedded
import androidx.room.Relation

data class HabitOwner(
    @Embedded val userLocal: UserLocal,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "habit_id"
    )
    val habitList: List<HabitLocal>
)