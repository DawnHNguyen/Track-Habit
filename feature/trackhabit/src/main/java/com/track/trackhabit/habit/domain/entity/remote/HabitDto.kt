package com.track.trackhabit.habit.domain.entity.remote

import androidx.room.PrimaryKey
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import java.util.*

data class HabitDto(
    @PrimaryKey(autoGenerate = true) val habitId: Int,
    val title: String,
    val description: String,
    val time: Date,
    val performances: List<Inspection>,
    val frequency: String?
) : RemoteDto {
    override fun mapToDomainModel() = Habit(habitId, title, description, time, performances,frequency)

    override fun mapToLocalDto() = HabitLocal(habitId, title, description, time.time,frequency)
}