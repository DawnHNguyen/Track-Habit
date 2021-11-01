package com.track.trackhabit.habit.presentation.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import com.track.trackhabit.habit.presentation.constpackage.Const
import com.track.trackhabit.habit.presentation.constpackage.ConstIdChannel
import com.track.trackhabit.habit.presentation.constpackage.ConstRequestCode

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setCancel(){
        setCancel(getPendingIntent(
            getIntent().apply {
                action = Const.CANCEL_ALARM_TIME
                putExtra(Const.EXTRA_EXACT_ALARM_TIME,0L)
                Log.d("checkCancel","Status: OK")
            }
        ))

    }

    fun setSnoozeAlarm(){
         setElapse(getPendingIntent(
            getIntent().apply {
                action = Const.SET_SNOOZE_ALARM_TIME
                putExtra(Const.EXTRA_EXACT_ALARM_TIME, 1L)
            }
        ))
    }

    fun setRepeating(timeInMillis: Long){
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Const.ACTION_SET_REPETITIVE_EXACT
                    putExtra(Const.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                }
            )
        )
    }


    private fun setCancel(pendingIntent: PendingIntent){
        alarmManager?.cancel(pendingIntent)
        Log.d("checkPenDing","${pendingIntent} ")
    }

    private fun setElapse(pendingIntent: PendingIntent){
        alarmManager.let {
            it?.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + Const.ELAPSE_5M,
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

    private fun getPendingIntent(intent: Intent) = PendingIntent.getBroadcast(context,ConstRequestCode.requestCodeReceiver, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    private fun getIntent() = Intent(context, AlarmReceiver::class.java)
}