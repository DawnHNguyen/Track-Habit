package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        username: String,
        password: String,
        fullname: String
    ) = repository.register(email, username, password, fullname)
}