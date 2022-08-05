package com.track.common.base.data.remote.services

import com.track.common.base.data.remote.dto.RefreshTokenRequest
import javax.inject.Inject

class TrackHabitCommonDataSource @Inject constructor(private val service: TrackHabitCommonService) {
    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest) = service.refreshToken(refreshTokenRequest)

}