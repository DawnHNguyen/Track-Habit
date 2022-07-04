package com.track.trackhabit.habit.data.remote.auth.services

import com.track.trackhabit.habit.data.remote.auth.dto.EmailTokenRequest
import com.track.trackhabit.habit.data.remote.auth.dto.LoginRequest
import com.track.trackhabit.habit.data.remote.auth.dto.RegisterRequest
import com.track.trackhabit.habit.data.remote.auth.dto.VerifyEmailTokenRequest
import com.track.trackhabit.habit.data.remote.auth.dto.response.EmailTokenResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.RegisterResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.VerifyEmailTokenResponse
import com.track.trackhabit.habit.data.remote.util.Resource
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Resource<RegisterResponse>

    @POST("auth/send-token")
    suspend fun getEmailToken(@Body emailTokenRequest: EmailTokenRequest): Resource<EmailTokenResponse>

    @POST("auth/verify-token")
    suspend fun verifyEmailToken(@Body verifyEmailTokenRequest: VerifyEmailTokenRequest): Resource<VerifyEmailTokenResponse>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Resource<LoginResponse>

}