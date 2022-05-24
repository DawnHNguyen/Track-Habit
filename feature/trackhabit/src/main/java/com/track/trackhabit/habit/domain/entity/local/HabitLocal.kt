package com.track.trackhabit.habit.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.track.common.base.dto.LocalDto
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.remote.HabitDto
import java.util.*

@Entity(foreignKeys = [ForeignKey(
    entity = UserLocal::class,
    parentColumns = arrayOf("user_id"),
    childColumns = arrayOf("user_id"),
    onDelete = CASCADE
)])
data class HabitLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "habit_id") val habitId: Int,
    val title: String,
    val time: Long,
    val frequency: String?
) : LocalDto {
    @ColumnInfo(name = "user_id")
    lateinit var userId: String
    lateinit var performance: List<Inspection>
    override fun mapToDomainModel() = Habit(habitId, title, time = Date().apply { time = this@HabitLocal.time }, performance, frequency)

    override fun mapToRemoteDto() = HabitDto(habitId, title, time = Date().apply { time = this@HabitLocal.time }, emptyList(),frequency)
}