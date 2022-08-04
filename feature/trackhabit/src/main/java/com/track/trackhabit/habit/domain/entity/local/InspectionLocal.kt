package com.track.trackhabit.habit.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.track.common.base.dto.LocalDto
import com.track.common.base.utils.DISPLAY_DATE_FORMAT
import com.track.common.base.utils.toFormattedString
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.remote.InspectionDto
import java.util.*

@Entity(foreignKeys = [ForeignKey(
    entity = HabitLocal::class,
    parentColumns = arrayOf("habit_id"),
    childColumns = arrayOf("habit_id"),
    onDelete = ForeignKey.CASCADE

)])
data class InspectionLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "inspection_id") val inspectionId: Int,
    val time: Long,
    val check: Boolean,
    @ColumnInfo(name = "createAt", defaultValue = "0")
    val createAt: Long,
    @ColumnInfo(name = "updateAt", defaultValue = "0")
    val updateAt: Long
) : LocalDto {
    @ColumnInfo(name = "habit_id")
    var id: Int = -1
    override fun mapToDomainModel() = Inspection(
        inspectionId,
        time = Date(time),
        check,
        Date().apply { time = createAt },
        Date().apply { time = updateAt }
    )

    override fun mapToRemoteDto() = InspectionDto(
        inspectionId,
        time = Date(time),
        check,
        Date().apply { time = createAt }.toFormattedString(DISPLAY_DATE_FORMAT),
        Date().apply { time = updateAt }.toFormattedString(DISPLAY_DATE_FORMAT)
    )
}