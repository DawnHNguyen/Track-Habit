package com.track.common.base.data.repository

import com.orhanobut.hawk.Hawk
import com.track.common.base.constpackage.HawkKey
import com.track.common.base.data.remote.dto.RefreshTokenRequest
import com.track.common.base.data.remote.dto.response.RefreshTokenResponse
import com.track.common.base.data.remote.services.TrackHabitCommonDataSource
import com.track.common.base.data.remote.util.Resource
import com.track.common.base.domain.repository.CommonRepository
import javax.inject.Inject

class CommonRepositoryImpl@Inject constructor(private val trackHabitCommonDataSource: TrackHabitCommonDataSource): CommonRepository {
    override suspend fun refreshToken(): Resource<RefreshTokenResponse> {
        val refreshToken = Hawk.get<String>(HawkKey.REFRESH_TOKEN)
        val refreshTokenRequest = RefreshTokenRequest(refreshToken)
        val refreshTokenResponse = trackHabitCommonDataSource.refreshToken(refreshTokenRequest)
        if (refreshTokenResponse.isSuccessful()) Hawk.put(HawkKey.ACCESS_TOKEN, refreshTokenResponse.data?.accessToken)
        return refreshTokenResponse
    }
}