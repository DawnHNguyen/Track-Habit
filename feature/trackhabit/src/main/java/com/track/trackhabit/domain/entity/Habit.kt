package com.track.trackhabit.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.local.HabitLocal
import com.track.trackhabit.domain.entity.remote.HabitDto
import java.util.*

data class Habit(
    val habitId: Int,
    val title: String,
    val description: String,
    val time: Date,
    val performances: List<Inspection>
) : DomainModel {
    override fun toLocalDto() = HabitLocal(habitId, title, description, time.time)

    override fun toRemoteDto() = HabitDto(habitId, title, description, time, performances)

}
