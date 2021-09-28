package com.track.trackhabit.domain.entity.local

import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.Habit
import com.track.trackhabit.domain.entity.User
import com.track.trackhabit.domain.entity.remote.UserDto

data class UserLocal(
    @PrimaryKey val id: String,
    val name: String,
    val habits: List<Habit>
): LocalDto {
    override fun mapToDomainModel() = User(id,name, habits)

    override fun mapToRemoteDto() = UserDto(id, name, habits)
}