package com.track.trackhabit.habit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.track.trackhabit.habit.data.local.dao.InspectionDao
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.repository.InspectionRepository

class InspectionRepositoryImpl(private val inspectionDao: InspectionDao): InspectionRepository {
    override suspend fun addInspection(inspection: Inspection) {
        inspectionDao.insertInspection(inspection.toLocalDto())
    }

    override suspend fun getInspection(): LiveData<List<Inspection>> =
        Transformations.map(inspectionDao.getInspection()) { list ->
            list.map { it.mapToDomainModel() }
        }

}