package com.track.trackhabit.habit.domain.entity.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
@Entity
data class HabitOwner(
    @Embedded val userLocal: UserLocal,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "habit_id"
    )
    val habitList: List<HabitLocal>
)