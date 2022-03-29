package com.track.common.base.utils

import java.util.*

fun convertStringToCalender(timeFormatted: String): Calendar{
    val arr = timeFormatted.split(':')
    val cal = Calendar.getInstance()
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    cal.set(Calendar.HOUR_OF_DAY, arr[0].toInt())
    cal.set(Calendar.MINUTE, arr[1].toInt())
    return cal
}