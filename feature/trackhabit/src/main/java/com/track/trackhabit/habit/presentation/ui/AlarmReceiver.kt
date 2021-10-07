package com.track.trackhabit.habit.presentation.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.track.trackhabit.habit.R


class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        createChannel(context)

        val builder = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("receive Alarm manager")
            .setContentText("This is an alarm")
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        with(NotificationManagerCompat.from(context)) {
            notify(12, builder.build())
        }
    }
}
