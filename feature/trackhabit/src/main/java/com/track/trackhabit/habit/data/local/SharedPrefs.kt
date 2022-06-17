package com.track.trackhabit.habit.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.track.trackhabit.habit.presentation.constpackage.Const
import javax.inject.Inject

class SharedPrefs @Inject constructor(private val context: Context) {
    private fun putStringSharedPreferences(
        PREF_NAME: String,
        valueName: String,
        value: String
    ) {
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putString(valueName, value)
            .apply()
    }

    private fun getStringSharedPreferences(
        PREF_NAME: String,
        stringName: String,
        defaultValue: String
    ): String =
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(stringName, defaultValue)
            ?: defaultValue

    fun getLanguagePreferences(defaultValue: String): String =
        getStringSharedPreferences(Const.LANGUAGE_PREF, Const.LANGUAGE_CODE, defaultValue)


    fun putLanguagePreferences(value: String) {
        putStringSharedPreferences(Const.LANGUAGE_PREF, Const.LANGUAGE_CODE, value)
    }
}