package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import javax.inject.Inject

class GetHabitByIdUseCase @Inject constructor(
    private val repository: TrackHabitRepository
) {
    suspend operator fun invoke(id: Int) = repository.getHabitById(id)
}