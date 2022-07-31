package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.UserRepository
import javax.inject.Inject

class GetValueIsUseAccUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): Boolean = repository.getValueIsUseAcc()
}