package com.track.trackhabit.habit.domain.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.track.common.base.dto.LocalDto
import com.track.common.base.utils.DISPLAY_DATE_FORMAT
import com.track.common.base.utils.toFormattedString
import com.track.trackhabit.habit.domain.entity.User
import com.track.trackhabit.habit.domain.entity.remote.UserDto
import java.util.*

@Entity
data class UserLocal(
    @PrimaryKey
    @ColumnInfo(name = "user_id") val userId: String,
    val name: String,
    val createAt: Long,
    val updateAt: Long
) : LocalDto {
    override fun mapToDomainModel() = User(
        userId,
        name,
        emptyList(),
        Date().apply { time = createAt },
        Date().apply { time = updateAt }
    )

    override fun mapToRemoteDto() = UserDto(
        userId,
        name,
        emptyList(),
        Date().apply { time = createAt }.toFormattedString(DISPLAY_DATE_FORMAT),
        Date().apply { time = updateAt }.toFormattedString(DISPLAY_DATE_FORMAT)
    )
}