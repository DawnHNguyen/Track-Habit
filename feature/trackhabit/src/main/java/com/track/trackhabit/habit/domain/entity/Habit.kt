package com.track.trackhabit.habit.domain.entity

import com.track.common.base.DomainModel
import com.track.common.base.utils.DISPLAY_DATE_FORMAT
import com.track.common.base.utils.toFormattedString
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import com.track.trackhabit.habit.domain.entity.remote.HabitDto
import java.util.*

data class Habit(
    val habitId: Int,
    val title: String,
    val time: Date,
    var performances: List<Inspection>,
    val frequency: String?,
    val createAt: Date,
    val updateAt: Date
) : DomainModel {
    override fun toLocalDto() = HabitLocal(
        habitId,
        title,
        time.time,
        frequency,
        createAt.time,
        updateAt.time
    ).apply { userId = "AAAA" }

    override fun toRemoteDto() = HabitDto(
        habitId,
        title,
        time,
        performances,
        frequency,
        createAt.toFormattedString(DISPLAY_DATE_FORMAT),
        updateAt.toFormattedString(DISPLAY_DATE_FORMAT)
    )

    fun isNotiToday(): Boolean {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val notifyMonday = frequency!![0] == '1'
        val notifyTuesday = frequency[1] == '1'
        val notifyWednesday = frequency[2] == '1'
        val notifyThursday = frequency[3] == '1'
        val notifyFriday = frequency[4] == '1'
        val notifySaturday = frequency[5] == '1'
        val notifySunday = frequency[6] == '1'
        return when (today) {
            Calendar.MONDAY -> notifyMonday

            Calendar.TUESDAY -> notifyTuesday

            Calendar.WEDNESDAY -> notifyWednesday

            Calendar.THURSDAY -> notifyThursday

            Calendar.FRIDAY -> notifyFriday

            Calendar.SATURDAY -> notifySaturday

            Calendar.SUNDAY -> notifySunday

            else -> true
        }
    }
}
