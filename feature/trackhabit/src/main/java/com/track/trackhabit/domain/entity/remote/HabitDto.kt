package com.track.trackhabit.domain.entity.remote

import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.Habit
import com.track.trackhabit.domain.entity.Inspection
import com.track.trackhabit.domain.entity.local.HabitLocal
import java.util.*

data class HabitDto(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    val time: Date,
    val performances: List<Inspection>
) : RemoteDto {
    override fun mapToDomainModel() = Habit(id, title, description, time, performances)

    override fun mapToLocalDto() = HabitLocal(id, title, description, time, performances)
}