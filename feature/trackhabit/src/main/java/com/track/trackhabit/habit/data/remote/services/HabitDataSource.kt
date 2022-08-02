package com.track.trackhabit.habit.data.remote.services

import com.track.trackhabit.habit.data.remote.dto.HabitRequest
import com.track.trackhabit.habit.data.remote.dto.RefreshTokenRequest
import javax.inject.Inject

class HabitDataSource @Inject constructor(private val service: HabitService) {
    suspend fun addHabit(habitRequest: HabitRequest) = service.addHabit(habitRequest)

    suspend fun getHabit(before: String) = service.getHabit(before)

    suspend fun updateHabit(id: Int, habitRequest: HabitRequest) = service.updateHabit(id, habitRequest)

    suspend fun deleteHabit(id: Int) = service.deleteHabit(id)

    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest) = service.refreshToken(refreshTokenRequest)
}