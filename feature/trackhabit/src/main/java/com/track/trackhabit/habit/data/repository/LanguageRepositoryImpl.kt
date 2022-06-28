package com.track.trackhabit.habit.data.repository

import com.track.trackhabit.habit.data.local.SharedPrefs
import com.track.trackhabit.habit.domain.entity.LanguageCode
import com.track.trackhabit.habit.domain.repository.LanguageRepository
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(private val sharedPrefs: SharedPrefs): LanguageRepository {
    override fun getLanguage(): String {
        return sharedPrefs.getLanguagePreferences(LanguageCode.US.languageCode)
    }

    override fun addLanguagePreferences(languageCode: String) {
        sharedPrefs.putLanguagePreferences(languageCode)
    }
}