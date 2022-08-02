package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke() = repository.logout()
}