package com.track.trackhabit.habit.data.remote.services

import com.track.trackhabit.habit.data.remote.dto.AddHabitRequest
import javax.inject.Inject

class HabitDataSource @Inject constructor(private val service: HabitService) {
    suspend fun addHabit(createHabitRequest: AddHabitRequest) = service.addHabit(createHabitRequest)


}