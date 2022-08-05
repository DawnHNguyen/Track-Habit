package com.track.common.base.data.remote.services

import com.track.common.base.data.remote.dto.RefreshTokenRequest
import com.track.common.base.data.remote.dto.response.RefreshTokenResponse
import com.track.common.base.data.remote.util.Resource
import retrofit2.http.Body
import retrofit2.http.POST

interface TrackHabitCommonService {

    @POST("auth/refresh-token")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Resource<RefreshTokenResponse>

}