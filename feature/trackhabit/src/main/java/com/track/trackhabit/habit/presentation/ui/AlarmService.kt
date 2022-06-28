package com.track.trackhabit.habit.presentation.ui

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import com.track.trackhabit.habit.presentation.constpackage.Const

@SuppressLint("LogNotTimber")
class AlarmService(private val context: Context) {
    private val intent = getIntent()

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setCancelAlarm(habitId: Int, habitName: String) {
        val pendingIntent = getPendingIntent(intent.apply {
            action = Const.ACTION_SET_REPETITIVE_EXACT
            putExtra(Const.HABIT_NAME, habitName)
            putExtra(Const.HABIT_ID, habitId)
        }, habitId)
        Log.d("checkIntentCancel", " - $pendingIntent - $intent -${intent.extras}")
        setCancel(pendingIntent)
    }

    private fun setCancel(pendingIntent: PendingIntent) {
        alarmManager.cancel(pendingIntent)
    }

    fun setSleepReminder(timeInMillis: Long){
        val pendingIntent = getPendingIntent(intent.apply { action = Const.SET_REMIND_SLEEPTIME }, 0)
        setAlarm(timeInMillis, pendingIntent)
    }

    fun setCancelSleepReminder(){
        val pendingIntent = getPendingIntent(intent.apply { action = Const.SET_REMIND_SLEEPTIME }, 0)
        setCancel(pendingIntent)
    }

    fun setSnoozeAlarm(habitId: Int, habitName: String) {

        val pendingIntent = getPendingIntent(intent.apply {
            action = Const.SET_SNOOZE_ALARM_TIME
            putExtra(Const.EXTRA_EXACT_ALARM_TIME, 1L)
            putExtra(Const.HABIT_NAME, habitName)
        }, habitId)
        Log.d("checkIntentSnooze", " - $pendingIntent - $intent -${intent.extras}")
        setElapse(pendingIntent)

    }

    fun setRepeating(timeInMillis: Long, habitId: Int, habitName: String) {

        val pendingIntent = getPendingIntent(intent.apply {
            action = Const.ACTION_SET_REPETITIVE_EXACT
            putExtra(Const.HABIT_NAME, habitName)
            putExtra(Const.HABIT_ID, habitId)
        }, habitId)
        Log.d("checkIntentRepeat", " - $pendingIntent - $intent -${intent.extras}")
        setAlarm(
            timeInMillis,
            pendingIntent
        )
    }

    private fun setElapse(pendingIntent: PendingIntent) {
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + Const.ELAPSE_SNOOZE_NOTIFICATION_5M,
            pendingIntent
        )
    }

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }

    private fun getPendingIntent(intent: Intent, requestCode: Int) = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)
}