package com.track.trackhabit.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.local.HabitLocal
import com.track.trackhabit.domain.entity.remote.HabitDto
import java.util.*

@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    val time: Date,
    val performances: List<Inspection>
) : DomainModel {
    override fun toLocalDto() = HabitLocal(id, title, description, time, performances)

    override fun toRemoteDto() = HabitDto(id, title, description, time, performances)

}
