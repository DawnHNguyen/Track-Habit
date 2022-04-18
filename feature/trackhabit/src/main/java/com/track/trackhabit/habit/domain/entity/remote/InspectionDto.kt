package com.track.trackhabit.habit.domain.entity.remote

import androidx.room.PrimaryKey
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import java.util.*

data class InspectionDto(
    @PrimaryKey
    val inspectionId: Int,
    val time: Date,
    val check: Boolean
) : RemoteDto {
    override fun mapToDomainModel() = Inspection(inspectionId,time, check)

    override fun mapToLocalDto() = InspectionLocal(inspectionId,time.time, check)
}