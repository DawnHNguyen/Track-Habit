package com.track.trackhabit.habit.domain.usecase

import com.track.common.base.data.remote.util.Resource
import com.track.trackhabit.habit.data.remote.dto.response.RefreshTokenResponse
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val repository: TrackHabitRepository) {
    suspend operator fun invoke(): Resource<RefreshTokenResponse> = repository.refreshToken()
}