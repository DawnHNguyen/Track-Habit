package com.track.trackhabit.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.local.UserLocal
import com.track.trackhabit.domain.entity.remote.UserDto

@Entity
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val habits: List<Habit>
) : DomainModel {

    override fun toLocalDto() = UserLocal(id, name, habits)

    override fun toRemoteDto() = UserDto(id, name, habits)

}