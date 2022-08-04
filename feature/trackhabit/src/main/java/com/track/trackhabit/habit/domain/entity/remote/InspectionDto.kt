package com.track.trackhabit.habit.domain.entity.remote

import androidx.room.PrimaryKey
import com.track.common.base.dto.RemoteDto
import com.track.common.base.utils.DISPLAY_DATE_FORMAT
import com.track.common.base.utils.toDate
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import java.util.*

data class InspectionDto(
    @PrimaryKey
    val inspectionId: Int,
    val time: Date,
    val check: Boolean,
    private val createAt: String,
    private val updateAt: String
) : RemoteDto {
    override fun mapToDomainModel() = Inspection(
        inspectionId,
        time,
        check,
        createAt = createAt.toDate(DISPLAY_DATE_FORMAT) ?: Date(),
        updateAt = updateAt.toDate(DISPLAY_DATE_FORMAT) ?: Date()
    )

    override fun mapToLocalDto() = InspectionLocal(
        inspectionId,
        time.time,
        check,
        createAt = createAt.toDate(DISPLAY_DATE_FORMAT)?.time ?: Date().time,
        updateAt = updateAt.toDate(DISPLAY_DATE_FORMAT)?.time ?: Date().time
    )
}