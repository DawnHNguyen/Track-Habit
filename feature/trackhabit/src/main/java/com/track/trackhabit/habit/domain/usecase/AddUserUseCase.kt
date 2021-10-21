package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.entity.User
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: TrackHabitRepository
) {
    suspend operator fun invoke(user: User) = repository.addUser(user)
}