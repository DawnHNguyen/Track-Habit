package com.track.trackhabit.habit.presentation.ui

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import com.track.trackhabit.habit.presentation.constpackage.Const
import com.track.trackhabit.habit.presentation.constpackage.ConstRequestCode

class AlarmService(private val context: Context) {
    private val intent = getIntent()
    private val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setCancelAlarm(habitId: Int){
        notificationManager.cancel(habitId)
    }

    fun setSnoozeAlarm(){

        val pendingIntent = getPendingIntent(intent.apply {
            action = Const.SET_SNOOZE_ALARM_TIME
            putExtra(Const.EXTRA_EXACT_ALARM_TIME, 1L)
        })
        Log.d("checkIntentSnooze"," - ${pendingIntent} - ${intent} -${intent.extras}")
        setElapse(pendingIntent)

    }

    fun setRepeating(timeInMillis: Long, habitId: Int){

        val pendingIntent = getPendingIntent(intent.apply {
            action = Const.ACTION_SET_REPETITIVE_EXACT
            putExtra(Const.EXTRA_EXACT_ALARM_TIME, timeInMillis)
            putExtra(Const.HABIT_ID, habitId)
        })
        Log.d("checkIntentRepeat"," - ${pendingIntent} - ${intent} -${intent.extras}")
        setAlarm(
            timeInMillis,
            pendingIntent
        )
    }

    private fun setElapse(pendingIntent: PendingIntent){
        alarmManager.let {
            it?.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + Const.ELAPSE_SNOOZE_NOTIFICATION_5M,
                pendingIntent)
        }
    }

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

    private fun getPendingIntent(intent: Intent) = PendingIntent.getBroadcast(context,ConstRequestCode.REQUEST_CODE_RECEIVER, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    private fun getIntent() = Intent(context, AlarmReceiver::class.java)
}