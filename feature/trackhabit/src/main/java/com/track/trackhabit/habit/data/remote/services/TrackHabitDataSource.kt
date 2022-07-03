package com.track.trackhabit.habit.data.remote.services

import com.track.trackhabit.habit.data.remote.dto.EmailTokenRequest
import com.track.trackhabit.habit.data.remote.dto.LoginRequest
import com.track.trackhabit.habit.data.remote.dto.RegisterRequest
import com.track.trackhabit.habit.data.remote.dto.VerifyEmailTokenRequest
import javax.inject.Inject

class TrackHabitDataSource @Inject constructor(
    private val service: TrackHabitService
) {
    suspend fun register(registerRequest: RegisterRequest) = service.register(registerRequest)

    suspend fun getEmailToken(emailTokenRequest: EmailTokenRequest) = service.getEmailToken(emailTokenRequest)

    suspend fun verifyEmailToken(verifyEmailTokenRequest: VerifyEmailTokenRequest) = service.verifyEmailToken(verifyEmailTokenRequest)

    suspend fun login(loginRequest: LoginRequest) = service.login(loginRequest)
}