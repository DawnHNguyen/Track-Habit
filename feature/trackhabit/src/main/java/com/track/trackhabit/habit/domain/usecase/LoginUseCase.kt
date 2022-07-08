package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.data.remote.auth.dto.LoginRequest
import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest) = repository.login(loginRequest)
}