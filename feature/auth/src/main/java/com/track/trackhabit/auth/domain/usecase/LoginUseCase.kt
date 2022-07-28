package com.track.trackhabit.auth.domain.usecase

import com.track.common.base.data.remote.util.Resource
import com.track.trackhabit.auth.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Resource<LoginResponse> =
        repository.login(username, password)
}