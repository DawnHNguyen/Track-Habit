package com.track.navigation

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

fun navigateToMainActivity(context: Context){
    val intent = Intent(context, Class.forName("com.track.trackhabit.habit.presentation.ui.MainActivity"))
    startActivity(context, intent, null)
}

fun navigateToAuthActivity(context: Context){
    val intent = Intent(context, Class.forName("com.track.trackhabit.auth.presentation.ui.AuthenticationActivity"))
    startActivity(context, intent, null)
}