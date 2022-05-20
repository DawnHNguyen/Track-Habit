package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.repository.InspectionRepository
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import javax.inject.Inject

class AddInspectionUseCase @Inject constructor(
    private val repository: InspectionRepository
) {
    suspend operator fun invoke(inspection: Inspection, habitId: Int) =
        repository.addInspectionToHabit(inspection, habitId)
}