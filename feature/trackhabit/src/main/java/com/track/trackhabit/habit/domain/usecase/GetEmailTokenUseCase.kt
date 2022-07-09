package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class GetEmailTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String) = repository.getEmailToken(email)
}