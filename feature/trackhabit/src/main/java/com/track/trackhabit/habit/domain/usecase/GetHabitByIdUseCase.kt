package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import javax.inject.Inject

class GetHabitByIdUseCase @Inject constructor(
    private val repository: TrackHabitRepository
) {
    operator fun invoke(id: Int) = repository.getHabitById(id)

    suspend fun getHabitValue(id: Int) = repository.getHabitValueById(id)
}