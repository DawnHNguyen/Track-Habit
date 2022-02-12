package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.device.TrackHabitNotificationManger
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import com.track.trackhabit.habit.presentation.ui.AlarmService
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(
    private val repository: TrackHabitRepository,
    private val alarmService: AlarmService,
    private val notificationManager: TrackHabitNotificationManger
) {
    suspend operator fun invoke(habit: Habit) {
        val newHabitID = repository.addHabit(habit)
        alarmService.setRepeating(habit.time.time)
        notificationManager.setNotification(newHabitID.toInt())
    }
}