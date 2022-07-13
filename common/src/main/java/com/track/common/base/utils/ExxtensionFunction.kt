package com.track.common.base.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val inputMethodManager =  getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.run {
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }

}