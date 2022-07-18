package com.track.common.base.utils

import java.util.*

fun checkIsTodayDateFormatValue(time: Date): Boolean{
    val cal = Calendar.getInstance().apply {
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val calLast = Calendar.getInstance().apply {
        set(Calendar.HOUR, 24)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return time in cal.time..calLast.time
}