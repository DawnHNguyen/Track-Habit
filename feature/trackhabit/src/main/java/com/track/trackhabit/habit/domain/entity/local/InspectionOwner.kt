package com.track.trackhabit.habit.domain.entity.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class InspectionOwner(
    @Embedded val habit: HabitLocal,
    @Relation(
        parentColumn = "habit_id",
        entityColumn = "habit_id"
    )
    val listInspection: List<InspectionLocal>
)