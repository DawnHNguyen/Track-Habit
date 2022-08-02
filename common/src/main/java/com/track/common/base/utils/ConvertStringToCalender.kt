package com.track.common.base.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val DISPLAY_DATE_FORMAT = "dd/MM/yyyy"

fun convertStringToCalender(timeFormatted: String): Calendar{
    val arr = timeFormatted.split(':')
    val cal = Calendar.getInstance()
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    cal.set(Calendar.HOUR_OF_DAY, arr[0].toInt())
    cal.set(Calendar.MINUTE, arr[1].toInt())
    return cal
}

fun Date.toFormattedString(format: String): String {
    val format = SimpleDateFormat(format, Locale.US)
    return format.format(this)
}

fun String.toDate(format: String): Date? {
    val dateFormat = SimpleDateFormat(format, Locale.US)
    return try {
        dateFormat.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}