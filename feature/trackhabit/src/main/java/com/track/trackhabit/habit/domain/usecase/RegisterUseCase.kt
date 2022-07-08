package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.data.remote.auth.dto.RegisterRequest
import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(registerRequest: RegisterRequest) = repository.register(registerRequest)
}