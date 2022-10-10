package com.track.trackhabit.habit.data.remote.services

import com.track.trackhabit.habit.data.remote.dto.CreateInspectionRequest
import com.track.trackhabit.habit.data.remote.dto.response.InspectionResponse
import com.track.trackhabit.habit.domain.entity.remote.InspectionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InspectionService {
    @POST("inspection")
    suspend fun addInspection(@Body inspectionRequest: CreateInspectionRequest): Response<InspectionResponse>

    @GET("inspection")
    suspend fun getInspection(): Response<List<InspectionDto>>
}