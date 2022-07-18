package com.track.common.base.utils

import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard(){
    activity?.hideKeyboard()
}