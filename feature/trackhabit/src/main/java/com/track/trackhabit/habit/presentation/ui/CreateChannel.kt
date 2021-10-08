package com.track.trackhabit.habit.presentation.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import com.track.trackhabit.habit.R


fun createChannel(context: Context){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(context.getString(R.string.featureTrackhabit_channelId_notification),
                context.getString(R.string.featureTrackhabit_nameOfChannel_notification),
                importance).apply {
            description = "dong mo ta nay that vo dung"
        }

        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}