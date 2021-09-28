package com.track.trackhabit.domain.entity.local

import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.Inspection
import com.track.trackhabit.domain.entity.remote.InspectionDto
import java.util.*

data class InspectionLocal(
    @PrimaryKey val time: Date,
    val check: Boolean
): LocalDto {
    override fun mapToDomainModel() = Inspection(time, check)

    override fun mapToRemoteDto() = InspectionDto(time, check)
}