package com.track.trackhabit.auth.domain.usecase

import com.track.trackhabit.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        username: String,
        password: String,
        fullname: String
    ) = repository.register(email, username, password, fullname)
}