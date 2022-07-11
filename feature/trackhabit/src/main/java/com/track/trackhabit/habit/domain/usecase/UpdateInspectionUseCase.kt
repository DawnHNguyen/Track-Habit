package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.repository.InspectionRepository
import javax.inject.Inject

class UpdateInspectionUseCase @Inject constructor(private val repository: InspectionRepository) {
    suspend operator fun invoke(inspection: Inspection, habitId: Int) = repository.updateInspectionToHabit(inspection, habitId)
}