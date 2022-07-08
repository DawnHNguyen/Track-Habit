package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.data.remote.auth.dto.VerifyEmailTokenRequest
import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyEmailTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(verifyEmailTokenRequest: VerifyEmailTokenRequest) = repository.verifyEmailToken(verifyEmailTokenRequest)
}