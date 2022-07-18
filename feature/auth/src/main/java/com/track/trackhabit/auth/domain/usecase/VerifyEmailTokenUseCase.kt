package com.track.trackhabit.auth.domain.usecase

import com.track.trackhabit.auth.data.remote.auth.dto.response.VerifyEmailTokenResponse
import com.track.trackhabit.auth.data.remote.util.Resource
import com.track.trackhabit.auth.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyEmailTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, code: String): Resource<VerifyEmailTokenResponse> =
        repository.verifyEmailToken(email, code)
}