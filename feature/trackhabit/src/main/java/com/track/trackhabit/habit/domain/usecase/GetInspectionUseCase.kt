package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import javax.inject.Inject

class GetInspectionUseCase @Inject constructor(
    private val repository: TrackHabitRepository
) {
    suspend operator fun invoke() = repository.getInspection()
}