package com.track.trackhabit.habit.presentation.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setCancel(){
        setCancel(getPendingIntent(
            getIntent().apply {
                action = "ACTION_SET_REPETITIVE_EXACT"
                putExtra("CANCEL_ALARM_TIME",0L)
            }
        ))
    }

    fun setRepeating(timeInMillis: Long){
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = "ACTION_SET_REPETITIVE_EXACT"
                    putExtra("EXTRA_EXACT_ALARM_TIME", timeInMillis)
                }
            )
        )
    }


    private fun getPendingIntent(intent: Intent) = PendingIntent.getBroadcast(context,12, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }
        }
    }

    private fun setCancel(pendingIntent: PendingIntent){
        alarmManager?.cancel(pendingIntent)
    }
    private fun getIntent() = Intent(context, AlarmReceiver::class.java)
}