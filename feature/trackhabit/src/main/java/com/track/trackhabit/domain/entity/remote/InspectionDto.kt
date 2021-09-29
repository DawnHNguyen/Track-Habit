package com.track.trackhabit.domain.entity.remote

import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.Inspection
import com.track.trackhabit.domain.entity.local.InspectionLocal
import java.util.*

data class InspectionDto(
    @PrimaryKey val time: Date,
    val check: Boolean
) : RemoteDto {
    override fun mapToDomainModel() = Inspection(time, check)

    override fun mapToLocalDto() = InspectionLocal(time, check)
}