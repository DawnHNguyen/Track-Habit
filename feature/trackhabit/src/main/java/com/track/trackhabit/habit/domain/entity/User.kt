package com.track.trackhabit.habit.domain.entity

import com.track.common.base.DomainModel
import com.track.common.base.utils.DISPLAY_DATE_FORMAT
import com.track.common.base.utils.toFormattedString
import com.track.trackhabit.habit.domain.entity.local.UserLocal
import com.track.trackhabit.habit.domain.entity.remote.UserDto
import java.util.*

data class User(
    val userId: String,
    val name: String,
    val habits: List<Habit>,
    val createAt: Date,
    val updateAt: Date
) : DomainModel {

    override fun toLocalDto() = UserLocal(userId, name, createAt.time, updateAt.time)

    override fun toRemoteDto() = UserDto(userId,
        name,
        habits,
        createAt.toFormattedString(DISPLAY_DATE_FORMAT),
        updateAt.toFormattedString(DISPLAY_DATE_FORMAT))

}