package com.track.common.base.domain.repository

import com.track.common.base.data.remote.dto.response.RefreshTokenResponse
import com.track.common.base.data.remote.util.Resource

interface CommonRepository {
    suspend fun refreshToken(): Resource<RefreshTokenResponse>
}