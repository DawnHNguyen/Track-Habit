package com.track.trackhabit.habit.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.track.common.base.dto.LocalDto
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.remote.HabitDto
import java.util.*

@Entity(foreignKeys = [ForeignKey(
    entity = UserLocal::class,
    parentColumns = arrayOf("user_id"),
    childColumns = arrayOf("huser_id"),
    onDelete = CASCADE
)])
data class HabitLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "habit_id") val habitId: Int,
    val title: String,
    val time: Long,
    val frequency: String?
) : LocalDto {
    @ColumnInfo(name = "huser_id")
    lateinit var userId: String
    override fun mapToDomainModel() = Habit(habitId, title, time = Date().apply { time = this@HabitLocal.time }, emptyList(), frequency)

    override fun mapToRemoteDto() = HabitDto(habitId, title, time = Date().apply { time = this@HabitLocal.time }, emptyList(),frequency)
}