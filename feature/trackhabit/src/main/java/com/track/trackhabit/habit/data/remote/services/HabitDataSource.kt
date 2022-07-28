package com.track.trackhabit.habit.data.remote.services

import com.track.trackhabit.habit.data.remote.dto.HabitRequest
import javax.inject.Inject

class HabitDataSource @Inject constructor(private val service: HabitService) {
    suspend fun addHabit(habitRequest: HabitRequest) = service.addHabit(habitRequest)


}