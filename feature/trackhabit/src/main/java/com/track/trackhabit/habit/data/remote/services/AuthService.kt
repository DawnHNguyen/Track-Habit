package com.track.trackhabit.habit.data.remote.services

import com.track.trackhabit.habit.data.remote.dto.emailtoken.EmailTokenRequest
import com.track.trackhabit.habit.data.remote.dto.emailtoken.EmailTokenResponse
import com.track.trackhabit.habit.data.remote.dto.login.LoginRequest
import com.track.trackhabit.habit.data.remote.dto.login.LoginResponse
import com.track.trackhabit.habit.data.remote.dto.register.RegisterRequest
import com.track.trackhabit.habit.data.remote.dto.register.RegisterResponse
import com.track.trackhabit.habit.data.remote.dto.verifytoken.VerifyEmailTokenRequest
import com.track.trackhabit.habit.data.remote.dto.verifytoken.VerifyEmailTokenResponse
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