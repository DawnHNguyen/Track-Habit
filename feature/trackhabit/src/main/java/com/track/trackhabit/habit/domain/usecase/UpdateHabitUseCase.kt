package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import com.track.trackhabit.habit.presentation.ui.AlarmService
import timber.log.Timber
import javax.inject.Inject

class UpdateHabitUseCase @Inject constructor(
    private val repository: TrackHabitRepository,
    private val alarmService: AlarmService,
) {
    suspend operator fun invoke(habit: Habit) {
        Timber.d("-->${habit}- usecase")
        alarmService.setRepeating(habit.time.time, habit.habitId, habit.title)
        return repository.updateHabit(habit)
    }
}