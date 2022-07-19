package com.track.trackhabit.auth.domain.usecase

import com.track.trackhabit.auth.data.remote.auth.dto.response.EmailTokenResponse
import com.track.trackhabit.auth.data.remote.util.Resource
import com.track.trackhabit.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetEmailTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Resource<EmailTokenResponse> = repository.getEmailToken(email)
}