package com.track.trackhabit.habit.data.remote.services

import com.track.trackhabit.habit.data.remote.dto.CreateInspectionRequest
import com.track.trackhabit.habit.domain.entity.remote.InspectionDto
import retrofit2.Response
import javax.inject.Inject

class InspectionDataSource @Inject constructor(private val service: InspectionService) {
    suspend fun addInspection(inspectionRequest: CreateInspectionRequest) = service.addInspection(inspectionRequest)

    suspend fun getInspection(): Response<List<InspectionDto>> = service.getInspection()
}