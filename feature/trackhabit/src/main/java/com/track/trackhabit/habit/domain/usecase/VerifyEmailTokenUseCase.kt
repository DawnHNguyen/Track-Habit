package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyEmailTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, code: String) =
        repository.verifyEmailToken(email, code)
}