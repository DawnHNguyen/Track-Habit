package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import timber.log.Timber
import javax.inject.Inject

class UpdateHabitUseCase @Inject constructor(
    private val repository: TrackHabitRepository
) {
    suspend operator fun invoke(habit: Habit){
        Timber.d("-->${habit}- usecase")
        return repository.updateHabit(habit)
    }
}