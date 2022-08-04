package com.track.trackhabit.habit.domain.entity

import com.track.common.base.DomainModel
import com.track.common.base.utils.DISPLAY_DATE_FORMAT
import com.track.common.base.utils.toFormattedString
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import com.track.trackhabit.habit.domain.entity.remote.InspectionDto
import java.util.*


data class Inspection(
    val inspectionId: Int,
    val time: Date,
    val check: Boolean,
    val createAt: Date,
    val updateAt: Date
) : DomainModel {
    override fun toLocalDto() = InspectionLocal(
        inspectionId,
        time.time,
        check,
        createAt.time,
        updateAt.time
    )

    override fun toRemoteDto() = InspectionDto(
        inspectionId,
        time,
        check,
        createAt.toFormattedString(DISPLAY_DATE_FORMAT),
        updateAt.toFormattedString(DISPLAY_DATE_FORMAT)
    )

}