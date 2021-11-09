package com.track.trackhabit.habit.presentation.ui

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.presentation.constpackage.Const
import com.track.trackhabit.habit.presentation.constpackage.ConstIdChannel
import com.track.trackhabit.habit.presentation.constpackage.ConstRequestCode
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        createChannel(context)
        val timeInMillis = intent.getLongExtra(Const.EXTRA_EXACT_ALARM_TIME, 0L)

        Log.d("onClickReceiver", "${intent.action} + ${intent} + ${intent.extras}")
        var alarmService = AlarmService(context)
        when (intent.action) {
            Const.START_SNOOZE_ALARM_TIME -> {
                setSnoozeAlarm(alarmService)
                with(NotificationManagerCompat.from(context)){
                    cancel(ConstIdChannel.ID_NOTIFICATION_1)
                }
            }

            Const.SET_SNOOZE_ALARM_TIME ->{
                buildNotification(context, "Set Snooze Time")
            }
            Const.CANCEL_ALARM_TIME -> {
                setCancelAlarmService(alarmService)
            }
            Const.ACTION_SET_REPETITIVE_EXACT -> {
                setRepetitiveAlarm(alarmService)
                if(isToday(intent)){
                    buildSnoozeNotification(context, "Set Repetitive Exact Time", convertDate(timeInMillis))
                }
            }
        }

    }

    private fun isToday(intent: Intent): Boolean{
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        when(today){
            Calendar.MONDAY -> {
                Log.d("checkm" ,"${intent.getBooleanExtra("MONDAY", true)}")
                if (intent.getBooleanExtra("MONDAY", true)){
                    return true
                }
                return false
            }
            Calendar.TUESDAY -> {
                Log.d("checkm" ,"${intent.getBooleanExtra("TUESDAY", true)}")
                if (intent.getBooleanExtra("TUESDAY", true)){
                    return true
                }
                return false
            }
            Calendar.WEDNESDAY -> {
                Log.d("checkm" ,"${intent.getBooleanExtra("WEDNESDAY", true)}")
                if(intent.getBooleanExtra("WEDNESDAY", true)){
                    return true
                }
                return false
            }
            Calendar.THURSDAY -> {
                Log.d("checkm" ,"${intent.getBooleanExtra("THURSDAY", true)}")
                if (intent.getBooleanExtra("THURSDAY", true)){
                    return true
                }
                return false
            }
            Calendar.FRIDAY -> {
                Log.d("checkm" ,"${intent.getBooleanExtra("FRIDAY", true)}")
                if (intent.getBooleanExtra("FRIDAY", true)){
                     return true
                }
                return false
            }
            Calendar.SATURDAY -> {
                Log.d("checkm" ,"${intent.getBooleanExtra("SATURDAY", true)}")
                if (intent.getBooleanExtra("SATURDAY", true)){
                    return true
                }
                return false
            }
            Calendar.SUNDAY -> {
                Log.d("checkm" ,"${intent.getBooleanExtra("SUNDAY", true)}")
                if (intent.getBooleanExtra("SUNDAY", true)){
                    return true
                }
                return false
            }
        }
        return true

    }

    private fun convertDate(timeInMillis: Long): String {
        return SimpleDateFormat("dd/MM/yyyy hh:mm").format(Date(timeInMillis))
    }

    private fun setRepetitiveAlarm(alarmService: AlarmService) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis += TimeUnit.HOURS.toMillis(24)
        }
        alarmService.setRepeating(cal.timeInMillis)
    }

    private fun setCancelAlarmService(alarmService: AlarmService){
        alarmService.setCancelAlarm()
    }
    private fun setSnoozeAlarm(alarmService: AlarmService){
        alarmService.setSnoozeAlarm()
    }

    private fun buildNotification(context: Context, title: String) {
        val intent = Intent(context ,HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, ConstRequestCode.REQUEST_CODE_ACTIVITY, intent, 0)

        val builder = NotificationCompat.Builder(context, ConstIdChannel.NOTIFICATION_1)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText("het thong bao roi day, co lam khong?")
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            notify(ConstIdChannel.ID_NOTIFICATION_SNOOZE, builder.build())
        }
    }

    private fun buildSnoozeNotification(context: Context, title: String, message: String) {
        val intent = Intent(context ,HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, ConstRequestCode.REQUEST_CODE_ACTIVITY, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val snoozeIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = Const.START_SNOOZE_ALARM_TIME
            putExtra(Const.EXTRA_EXACT_ALARM_TIME, 1L)
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, ConstRequestCode.REQUEST_CODE_ACTIVITY, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, ConstIdChannel.NOTIFICATION_1)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText("I got triggered at - $message")
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_launcher_foreground,"Delay 5m", snoozePendingIntent)
            .setAutoCancel(true)


        with(NotificationManagerCompat.from(context)) {
            notify(ConstIdChannel.ID_NOTIFICATION_1, builder.build())

        }
    }

}
