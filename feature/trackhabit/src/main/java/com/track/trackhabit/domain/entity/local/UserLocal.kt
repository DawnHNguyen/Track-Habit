package com.track.trackhabit.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.track.common.base.DomainModel
import com.track.common.base.dto.LocalDto
import com.track.common.base.dto.RemoteDto
import com.track.trackhabit.domain.entity.Habit
import com.track.trackhabit.domain.entity.User
import com.track.trackhabit.domain.entity.remote.UserDto

@Entity
data class UserLocal(
    @PrimaryKey
    @ColumnInfo(name = "user_id") val userId: String,
    val name: String,
) : LocalDto {
    override fun mapToDomainModel() = User(userId, name, emptyList())

    override fun mapToRemoteDto() = UserDto(userId, name, emptyList())
}