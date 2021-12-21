package com.track.trackhabit.habit.domain.entity

import com.track.common.base.DomainModel
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import com.track.trackhabit.habit.domain.entity.remote.HabitDto
import java.util.*

data class Habit(
    val habitId: Int,
    val title: String,
    val description: String,
    val time: Date,
    val performances: List<Inspection>,
    val frequency: String?
) : DomainModel {
    override fun toLocalDto() = HabitLocal(0, title, description, time.time,frequency)

    override fun toRemoteDto() = HabitDto(habitId, title, description, time, performances,frequency)

}
