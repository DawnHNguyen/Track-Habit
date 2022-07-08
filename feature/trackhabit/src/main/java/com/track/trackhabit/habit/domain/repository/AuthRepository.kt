package com.track.trackhabit.habit.domain.repository

import com.track.trackhabit.habit.data.remote.auth.dto.EmailTokenRequest
import com.track.trackhabit.habit.data.remote.auth.dto.LoginRequest
import com.track.trackhabit.habit.data.remote.auth.dto.RegisterRequest
import com.track.trackhabit.habit.data.remote.auth.dto.VerifyEmailTokenRequest
import com.track.trackhabit.habit.data.remote.auth.dto.response.EmailTokenResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.RegisterResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.VerifyEmailTokenResponse
import com.track.trackhabit.habit.data.remote.util.Resource


interface AuthRepository {
    suspend fun register(registerRequest: RegisterRequest): Resource<RegisterResponse>

    suspend fun getEmailToken(emailTokenRequest: EmailTokenRequest): Resource<EmailTokenResponse>

    suspend fun verifyEmailToken(verifyEmailTokenRequest: VerifyEmailTokenRequest): Resource<VerifyEmailTokenResponse>

    suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse>
}