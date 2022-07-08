package com.track.trackhabit.habit.data.repository

import com.track.trackhabit.habit.data.remote.auth.dto.EmailTokenRequest
import com.track.trackhabit.habit.data.remote.auth.dto.LoginRequest
import com.track.trackhabit.habit.data.remote.auth.dto.RegisterRequest
import com.track.trackhabit.habit.data.remote.auth.dto.VerifyEmailTokenRequest
import com.track.trackhabit.habit.data.remote.auth.dto.response.EmailTokenResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.RegisterResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.VerifyEmailTokenResponse
import com.track.trackhabit.habit.data.remote.auth.services.AuthDataSource
import com.track.trackhabit.habit.data.remote.util.Resource
import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource): AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): Resource<RegisterResponse> {
        return authDataSource.register(registerRequest)
    }

    override suspend fun getEmailToken(emailTokenRequest: EmailTokenRequest): Resource<EmailTokenResponse> {
        return authDataSource.getEmailToken(emailTokenRequest)
    }

    override suspend fun verifyEmailToken(verifyEmailTokenRequest: VerifyEmailTokenRequest): Resource<VerifyEmailTokenResponse> {
        return authDataSource.verifyEmailToken(verifyEmailTokenRequest)
    }

    override suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse> {
        return authDataSource.login(loginRequest)
    }
}