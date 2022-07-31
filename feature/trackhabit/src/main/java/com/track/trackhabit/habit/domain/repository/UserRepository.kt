package com.track.trackhabit.habit.domain.repository

import com.track.trackhabit.habit.domain.entity.User

interface UserRepository {
    suspend fun addUser(user: User)
    fun getValueIsUseAcc(): Boolean
}