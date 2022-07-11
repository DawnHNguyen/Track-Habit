package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String) =
        repository.login(username, password)
}