package com.track.trackhabit.habit.device

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.track.trackhabit.habit.R
import javax.inject.Inject

class TrackHabitNotificationManger @Inject constructor(private val context: Context) {
    fun setNotification(habitId: Int) {
        val builder = NotificationCompat.Builder(
            context,
            context.getString(R.string.featureTrackhabit_channelId_notification)
        )
            .setSmallIcon(R.mipmap.ic_trackhabit_logo)
            .setContentTitle(context.getString(R.string.featureTrackhabit_title_notification))
            .setContentText(context.getString(R.string.featureTrackhabit_content_notification))
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        with(NotificationManagerCompat.from(context)) {
            notify(habitId, builder.build())
        }
    }
}