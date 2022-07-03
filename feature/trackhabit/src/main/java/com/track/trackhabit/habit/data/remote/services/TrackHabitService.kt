package com.track.trackhabit.habit.data.remote.services

import com.track.trackhabit.habit.data.remote.dto.EmailTokenRequest
import com.track.trackhabit.habit.data.remote.dto.LoginRequest
import com.track.trackhabit.habit.data.remote.dto.RegisterRequest
import com.track.trackhabit.habit.data.remote.dto.VerifyEmailTokenRequest
import com.track.trackhabit.habit.data.remote.dto.response.EmailTokenResponse
import com.track.trackhabit.habit.data.remote.dto.response.LoginResponse
import com.track.trackhabit.habit.data.remote.dto.response.RegisterResponse
import com.track.trackhabit.habit.data.remote.dto.response.VerifyEmailTokenResponse
import com.track.trackhabit.habit.data.remote.util.Resource
import retrofit2.http.Body
import retrofit2.http.POST

interface TrackHabitService {
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Resource<RegisterResponse>

    @POST("auth/send-token")
    suspend fun getEmailToken(@Body emailTokenRequest: EmailTokenRequest): Resource<EmailTokenResponse>

    @POST("auth/verify-toekn")
    suspend fun verifyEmailToken(@Body verifyEmailTokenRequest: VerifyEmailTokenRequest): Resource<VerifyEmailTokenResponse>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Resource<LoginResponse>

}