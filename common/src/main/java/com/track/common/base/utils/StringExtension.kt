package com.track.common.base.utils

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}