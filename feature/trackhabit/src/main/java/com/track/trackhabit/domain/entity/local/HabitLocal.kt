package com.track.trackhabit.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.Habit
import com.track.trackhabit.domain.entity.Inspection
import com.track.trackhabit.domain.entity.remote.HabitDto
import java.util.*

@Entity(foreignKeys = [ForeignKey(
    entity = UserLocal::class,
    parentColumns = arrayOf("user_id"),
    childColumns = arrayOf("user_id")
)])
data class HabitLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "habit_id") val habitId: Int,
    val title: String,
    val description: String,
    val time: Long,
) : LocalDto {
    @ColumnInfo(name = "user_id")
    lateinit var userId: String
    override fun mapToDomainModel() = Habit(habitId, title, description, time = Date().apply { time = time }, emptyList())

    override fun mapToRemoteDto() = HabitDto(habitId, title, description,time = Date().apply { time = time }, emptyList())
}