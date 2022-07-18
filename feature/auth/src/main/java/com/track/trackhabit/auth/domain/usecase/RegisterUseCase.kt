package com.track.trackhabit.auth.domain.usecase

import com.track.trackhabit.auth.data.remote.auth.dto.response.RegisterResponse
import com.track.trackhabit.auth.data.remote.util.Resource
import com.track.trackhabit.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, username: String, password: String, fullName: String): Resource<RegisterResponse> =
        repository.register(email, username, password, fullName)
}