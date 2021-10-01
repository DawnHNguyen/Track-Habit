package com.track.trackhabit.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.local.InspectionLocal
import com.track.trackhabit.domain.entity.remote.InspectionDto
import java.util.*


data class Inspection(
    val time: Date,
    val check: Boolean
) : DomainModel {
    override fun toLocalDto() = InspectionLocal(time.time, check)

    override fun toRemoteDto() = InspectionDto(time, check)

}