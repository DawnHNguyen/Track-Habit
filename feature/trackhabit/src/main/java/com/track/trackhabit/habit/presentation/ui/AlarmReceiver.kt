package com.track.trackhabit.habit.presentation.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.MessageFormat.format
import android.os.Build
import android.text.format.DateFormat.format
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.track.trackhabit.habit.R
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        createChannel(context)
        val timeInMillis = intent.getLongExtra("EXTRA_EXACT_ALARM_TIME", 0L)

        when (intent.action) {
            "CANCEL_ALARM_TIME" -> {
                setCancelAlarm(AlarmService(context))
            }
            "ACTION_SET_REPETITIVE_EXACT" -> {
                setRepetitiveAlarm(AlarmService(context))
                buildNotification(context, "Set Repetitive Exact Time", convertDate(timeInMillis))
            }
        }

    }

    private fun convertDate(timeInMillis: Long): String {
        return SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Date(timeInMillis))
    }

    private fun setRepetitiveAlarm(alarmService: AlarmService) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis += TimeUnit.DAYS.toMillis(1)
        }
        alarmService.setRepeating(cal.timeInMillis)
    }

    private fun setCancelAlarm(alarmService: AlarmService){
        alarmService.setCancel()
    }

    private fun buildNotification(context: Context, title: String, message: String) {
        val builder = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText("I got triggered at - $message")
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        with(NotificationManagerCompat.from(context)) {
            notify(12, builder.build())
        }
    }

}
