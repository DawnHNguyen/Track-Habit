package com.track.trackhabit.habit.domain.entity

import com.track.common.base.DomainModel
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import com.track.trackhabit.habit.domain.entity.remote.InspectionDto
import java.util.*


data class Inspection(
    val inspectionId: Int,
    val time: Date,
    val check: Boolean
) : DomainModel {
    override fun toLocalDto() = InspectionLocal(inspectionId,time.time, check)

    override fun toRemoteDto() = InspectionDto(inspectionId,time, check)

}