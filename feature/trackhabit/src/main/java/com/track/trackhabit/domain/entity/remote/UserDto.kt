package com.track.trackhabit.domain.entity.remote

import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.Habit
import com.track.trackhabit.domain.entity.User
import com.track.trackhabit.domain.entity.local.UserLocal

class UserDto(
    @PrimaryKey val id: String,
    val name: String,
    private val habits: List<Habit>
) : RemoteDto {
    override fun mapToDomainModel() = User(id,name, habits)

    override fun mapToLocalDto() = UserLocal(id, name, habits)
}