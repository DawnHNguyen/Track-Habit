package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import com.track.trackhabit.habit.presentation.ui.AlarmService
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(
    private val repository: TrackHabitRepository,
    private val alarmService: AlarmService
) {
    suspend operator fun invoke(id: Int, name: String) {
        alarmService.setCancelAlarm(id, name)
        return repository.deleteHabit(id)
    }
}