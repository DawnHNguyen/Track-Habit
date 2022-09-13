package com.track.trackhabit.habit.domain.entity.remote

import androidx.room.PrimaryKey
import com.track.common.base.dto.RemoteDto
import com.track.common.base.utils.DISPLAY_DATE_FORMAT
import com.track.common.base.utils.toDate
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import java.util.*

data class HabitDto(
    @PrimaryKey(autoGenerate = true) val habitId: Int,
    val title: String,
    val time: Date,
    val performances: List<Inspection>,
    val frequency: String?,
    val createAt: String,
    val updateAt: String
) : RemoteDto {
    override fun mapToDomainModel() = Habit(
        habitId,
        title,
        time,
        performances,
        frequency,
        createAt = createAt.toDate(DISPLAY_DATE_FORMAT) ?: Date(),
        updateAt = updateAt.toDate(DISPLAY_DATE_FORMAT) ?: Date())

    override fun mapToLocalDto() = HabitLocal(
        habitId,
        title,
        time.time,
        frequency,
        createAt = createAt.toDate(DISPLAY_DATE_FORMAT)?.time ?: Date().time,
        updateAt = updateAt.toDate(DISPLAY_DATE_FORMAT)?.time ?: Date().time
    )
}