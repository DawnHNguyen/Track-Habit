package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.habit.data.remote.util.Resource
import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Resource<LoginResponse> =
        repository.login(username, password)
}