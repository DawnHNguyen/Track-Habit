package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.entity.User
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import com.track.trackhabit.habit.domain.repository.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) = repository.addUser(user)
}