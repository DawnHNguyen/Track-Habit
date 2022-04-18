package com.track.trackhabit.habit.domain.entity.local

import androidx.room.Embedded
import androidx.room.Relation

data class InspectionOwner(
    @Embedded val habit: HabitLocal,
    @Relation(
        parentColumn = "habitId",
        entityColumn = "inspectionId"
    )
    val listInspection: List<InspectionLocal>
)