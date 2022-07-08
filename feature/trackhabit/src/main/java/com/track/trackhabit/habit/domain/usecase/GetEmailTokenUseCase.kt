package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.data.remote.auth.dto.EmailTokenRequest
import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class GetEmailTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(emailTokenRequest: EmailTokenRequest) = repository.getEmailToken(emailTokenRequest)
}