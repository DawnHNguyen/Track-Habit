package com.track.trackhabit.auth.data.remote.auth.services

import com.track.trackhabit.auth.data.remote.auth.dto.EmailTokenRequest
import com.track.trackhabit.auth.data.remote.auth.dto.LoginRequest
import com.track.trackhabit.auth.data.remote.auth.dto.RegisterRequest
import com.track.trackhabit.auth.data.remote.auth.dto.VerifyEmailTokenRequest
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val service: AuthService
) {
    suspend fun register(registerRequest: RegisterRequest) = service.register(registerRequest)

    suspend fun getEmailToken(emailTokenRequest: EmailTokenRequest) = service.getEmailToken(emailTokenRequest)

    suspend fun verifyEmailToken(verifyEmailTokenRequest: VerifyEmailTokenRequest) = service.verifyEmailToken(verifyEmailTokenRequest)

    suspend fun login(loginRequest: LoginRequest) = service.login(loginRequest)
}