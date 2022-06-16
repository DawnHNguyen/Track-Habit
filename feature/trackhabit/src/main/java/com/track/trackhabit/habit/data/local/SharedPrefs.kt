package com.track.trackhabit.habit.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPrefs {
    fun putStringSharedPreferences(
        context: Context,
        PREF_NAME: String,
        stringName: String,
        string: String
    ) {
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putString(stringName, string).apply()
    }

    fun getStringSharedPreferences(
        context: Context,
        PREF_NAME: String,
        stringName: String,
        defaultValue: String
    ): String =
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(stringName, defaultValue)?: defaultValue
}