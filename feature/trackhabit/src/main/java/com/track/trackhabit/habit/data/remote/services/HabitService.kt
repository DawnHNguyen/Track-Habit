package com.track.trackhabit.habit.data.remote.services

import com.track.common.base.data.remote.util.Resource
import com.track.trackhabit.habit.data.remote.dto.HabitRequest
import com.track.trackhabit.habit.data.remote.dto.response.HabitResponse
import retrofit2.http.*

interface HabitService {
    @POST("habit")
    suspend fun addHabit(@Body habitRequest: HabitRequest): Resource<HabitResponse>

    @GET("habit")
    suspend fun getHabit(@Query("before") before: String, @Query("limit") limit: Int = 0, @Query("page") page: Int = 1): Resource<List<HabitResponse?>>

    @PATCH("habit/{id}")
    suspend fun updateHabit(@Path("id") id: Int, @Body habitRequest: HabitRequest): Resource<HabitResponse>

    @DELETE("habit/{id}")
    suspend fun deleteHabit(@Path("id") id: Int): Resource<HabitResponse>

}