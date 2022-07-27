package com.track.trackhabit.habit.data.remote.services

import com.track.common.base.data.remote.util.Resource
import com.track.trackhabit.habit.data.remote.dto.AddHabitRequest
import com.track.trackhabit.habit.data.remote.dto.response.AddHabitResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface HabitService {
    @POST("habit")
    suspend fun addHabit(@Body createHabitRequest: AddHabitRequest): Resource<AddHabitResponse>


}