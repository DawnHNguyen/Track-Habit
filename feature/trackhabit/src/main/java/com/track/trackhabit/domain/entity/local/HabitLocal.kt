package com.track.trackhabit.domain.entity.local

import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.Habit
import com.track.trackhabit.domain.entity.Inspection
import com.track.trackhabit.domain.entity.remote.HabitDto
import java.util.*

data class HabitLocal(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    val time: Date,
    val performances: List<Inspection>
) : LocalDto {
    override fun mapToDomainModel() = Habit(id, title, description, time, performances)

    override fun mapToRemoteDto() = HabitDto(id, title, description, time, performances)
}