package com.track.trackhabit.auth.domain.usecase

import com.track.trackhabit.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetEmailTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String) = repository.getEmailToken(email)
}