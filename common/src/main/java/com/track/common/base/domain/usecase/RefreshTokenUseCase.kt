package com.track.common.base.domain.usecase

import com.track.common.base.data.remote.dto.response.RefreshTokenResponse
import com.track.common.base.data.remote.util.Resource
import com.track.common.base.domain.repository.CommonRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val repository: CommonRepository) {
    suspend operator fun invoke(): Resource<RefreshTokenResponse> = repository.refreshToken()
}