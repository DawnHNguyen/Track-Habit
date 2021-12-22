package com.track.trackhabit.habit.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.track.common.base.dto.LocalDto
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.remote.HabitDto
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
    val frequency: String?
) : LocalDto {
    @ColumnInfo(name = "user_id")
    var userId: String = "AAAA"
    override fun mapToDomainModel() = Habit(habitId, title, description, time = Date().apply { time = time }, emptyList(), frequency)

    override fun mapToRemoteDto() = HabitDto(habitId, title, description,time = Date().apply { time = time }, emptyList(),frequency)
}