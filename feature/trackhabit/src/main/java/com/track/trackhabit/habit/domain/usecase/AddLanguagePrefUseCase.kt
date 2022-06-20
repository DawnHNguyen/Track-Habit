package com.track.trackhabit.habit.domain.usecase

import com.track.trackhabit.habit.domain.repository.LanguageRepository
import javax.inject.Inject

class AddLanguagePrefUseCase @Inject constructor(private val repository: LanguageRepository) {
    suspend operator fun invoke(languageCode: String) = repository.addLanguagePreferences(languageCode)
}