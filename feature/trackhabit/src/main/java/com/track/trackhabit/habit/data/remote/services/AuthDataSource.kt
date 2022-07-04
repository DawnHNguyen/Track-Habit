package com.track.trackhabit.habit.data.remote.services

import com.track.trackhabit.habit.data.remote.dto.emailtoken.EmailTokenRequest
import com.track.trackhabit.habit.data.remote.dto.login.LoginRequest
import com.track.trackhabit.habit.data.remote.dto.register.RegisterRequest
import com.track.trackhabit.habit.data.remote.dto.verifytoken.VerifyEmailTokenRequest
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val service: AuthService
) {
    suspend fun register(registerRequest: RegisterRequest) = service.register(registerRequest)

    suspend fun getEmailToken(emailTokenRequest: EmailTokenRequest) = service.getEmailToken(emailTokenRequest)

    suspend fun verifyEmailToken(verifyEmailTokenRequest: VerifyEmailTokenRequest) = service.verifyEmailToken(verifyEmailTokenRequest)

    suspend fun login(loginRequest: LoginRequest) = service.login(loginRequest)
}