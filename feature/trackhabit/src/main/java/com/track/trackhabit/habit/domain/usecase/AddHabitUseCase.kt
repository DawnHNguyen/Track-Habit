package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(
    private val repository: TrackHabitRepository
) {
    suspend operator fun invoke(habit: Habit) {
        val newHabitID = repository.addHabit(habit)
    }
}