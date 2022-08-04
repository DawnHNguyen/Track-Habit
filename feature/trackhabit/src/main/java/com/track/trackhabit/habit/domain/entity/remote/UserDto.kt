package com.track.trackhabit.habit.domain.entity.remote

import androidx.room.PrimaryKey
import com.track.common.base.dto.RemoteDto
import com.track.common.base.utils.DISPLAY_DATE_FORMAT
import com.track.common.base.utils.toDate
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.User
import com.track.trackhabit.habit.domain.entity.local.UserLocal
import java.util.*

class UserDto(
    @PrimaryKey val userId: String,
    val name: String,
    private val habits: List<Habit>,
    private val createAt: String,
    private val updateAt: String
) : RemoteDto {
    override fun mapToDomainModel() = User(
        userId,
        name,
        habits,
        createAt = createAt.toDate(DISPLAY_DATE_FORMAT) ?: Date(),
        updateAt = updateAt.toDate(DISPLAY_DATE_FORMAT) ?: Date()
    )

    override fun mapToLocalDto() = UserLocal(
        userId,
        name,
        createAt = createAt.toDate(DISPLAY_DATE_FORMAT)?.time ?: Date().time,
        updateAt = updateAt.toDate(DISPLAY_DATE_FORMAT)?.time ?: Date().time
    )
}