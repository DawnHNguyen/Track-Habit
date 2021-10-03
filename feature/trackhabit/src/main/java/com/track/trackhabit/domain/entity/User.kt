package com.track.trackhabit.domain.entity

import com.track.common.base.DomainModel
import com.track.trackhabit.domain.entity.local.UserLocal
import com.track.trackhabit.domain.entity.remote.UserDto

data class User(
    val userId: String,
    val name: String,
    val habits: List<Habit>
) : DomainModel {

    override fun toLocalDto() = UserLocal(userId, name)

    override fun toRemoteDto() = UserDto(userId, name, habits)

}