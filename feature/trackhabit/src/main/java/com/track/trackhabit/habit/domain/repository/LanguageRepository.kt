package com.track.trackhabit.habit.domain.repository


interface LanguageRepository {
    fun getLanguage(): String
    fun addLanguagePreferences(languageCode: String)
}