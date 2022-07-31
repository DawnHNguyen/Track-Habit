package com.track.trackhabit.auth.domain.usecase

import com.track.trackhabit.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SkipAccountUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.skipAccount()
}