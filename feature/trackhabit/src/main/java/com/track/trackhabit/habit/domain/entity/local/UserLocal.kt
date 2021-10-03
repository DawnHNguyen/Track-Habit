package com.track.trackhabit.habit.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.track.common.base.dto.LocalDto
import com.track.trackhabit.habit.domain.entity.User
import com.track.trackhabit.habit.domain.entity.remote.UserDto

@Entity
data class UserLocal(
    @PrimaryKey
    @ColumnInfo(name = "user_id") val userId: String,
    val name: String,
) : LocalDto {
    override fun mapToDomainModel() = User(userId, name, emptyList())

    override fun mapToRemoteDto() = UserDto(userId, name, emptyList())
}