package com.track.trackhabit.habit.domain.entity.remote

import androidx.room.PrimaryKey
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.User
import com.track.trackhabit.habit.domain.entity.local.UserLocal

class UserDto(
    @PrimaryKey val userId: String,
    val name: String,
    private val habits: List<Habit>
) : RemoteDto {
    override fun mapToDomainModel() = User(userId, name, habits)

    override fun mapToLocalDto() = UserLocal(userId, name)
}