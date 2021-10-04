package com.track.trackhabit.habit.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.track.common.base.dto.LocalDto
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.remote.InspectionDto
import java.util.*

@Entity(foreignKeys = [ForeignKey(
    entity = HabitLocal::class,
    parentColumns = arrayOf("habit_id"),
    childColumns = arrayOf("habit_id")
)])
data class InspectionLocal(
    @PrimaryKey val time: Long,
    val check: Boolean
) : LocalDto {
    @ColumnInfo(name = "habit_id")
    lateinit var id: String
    override fun mapToDomainModel() = Inspection(time = Date().apply { time = time }, check)

    override fun mapToRemoteDto() = InspectionDto(time = Date().apply { time = time }, check)
}