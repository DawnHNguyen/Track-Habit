package com.track.trackhabit.habit.domain.repository

import androidx.lifecycle.LiveData
import com.track.trackhabit.habit.domain.entity.Inspection

interface InspectionRepository {
    suspend fun getInspection(): LiveData<List<Inspection>>
    suspend fun addInspection(inspection: Inspection)
}