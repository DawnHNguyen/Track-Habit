package com.track.trackhabit.habit.presentation.ui

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.presentation.constpackage.Const
import com.track.trackhabit.habit.presentation.constpackage.ConstIdChannel
import com.track.trackhabit.habit.presentation.constpackage.ConstRequestCode
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        createChannel(context)

        Log.d("onClickReceiver", "${intent.action} + ${intent} + ${intent.extras}")
        var alarmService = AlarmService(context)
        Log.d("CheckAction", "-action ${intent.action}")
        when (intent.action) {
            Const.START_SNOOZE_ALARM_TIME -> {
                setSnoozeAlarm(alarmService)
                with(NotificationManagerCompat.from(context)) {
                    cancel(ConstIdChannel.ID_NOTIFICATION_1)
                }
            }

            Const.SET_SNOOZE_ALARM_TIME -> {
                buildNotification(context, "Set Snooze Time")
            }

            Const.ACTION_SET_REPETITIVE_EXACT -> {
                setRepetitiveAlarm(alarmService, intent.getIntExtra(Const.HABIT_ID, 0), intent.getStringExtra(Const.HABIT_NAME).toString())
                if (isToday(intent)) {
                    buildSnoozeNotification(
                        context,
                        context.getString(R.string.featureTrackhabit_title_notification),
                        intent.getStringExtra(Const.HABIT_NAME).toString(),
                        intent.getIntExtra(Const.HABIT_ID, 0)
                    )
                }
            }

            Const.SET_REMIND_SLEEPTIME -> {
                val builder = NotificationCompat.Builder(context, ConstIdChannel.REMIND_SLEEP_NOTIFICATION)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(context.getString(R.string.sleepNoti_title))
                    .setContentText(context.getString(R.string.sleepNoti_content))
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                with(NotificationManagerCompat.from(context)) {
                    notify(ConstIdChannel.ID_REMIND_SLEEP_NOTIFICATION, builder.build())
                }
            }
        }

    }

    private fun isToday(intent: Intent): Boolean {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        Log.d("checkm", "${intent.getBooleanExtra("MONDAY", false)}")
        Log.d("checktu", "${intent.getBooleanExtra("TUESDAY", false)}")
        Log.d("checkwe", "${intent.getBooleanExtra("WEDNESDAY", false)}")
        Log.d("checkmth", "${intent.getBooleanExtra("THURSDAY", false)}")
        Log.d("checkfr", "${intent.getBooleanExtra("FRIDAY", false)}")
        Log.d("checksa", "${intent.getBooleanExtra("SATURDAY", false)}")
        Log.d("checksu", "${intent.getBooleanExtra("SUNDAY", false)}")

        when (today) {
            Calendar.MONDAY -> {
                if (intent.getBooleanExtra("MONDAY", true)) {
                    return true
                }
                return false
            }
            Calendar.TUESDAY -> {

                if (intent.getBooleanExtra("TUESDAY", true)) {
                    return true
                }
                return false
            }
            Calendar.WEDNESDAY -> {
                if (intent.getBooleanExtra("WEDNESDAY", true)) {
                    return true
                }
                return false
            }
            Calendar.THURSDAY -> {
                if (intent.getBooleanExtra("THURSDAY", true)) {
                    return true
                }
                return false
            }
            Calendar.FRIDAY -> {
                if (intent.getBooleanExtra("FRIDAY", true)) {
                    return true
                }
                return false
            }
            Calendar.SATURDAY -> {
                if (intent.getBooleanExtra("SATURDAY", true)) {
                    return true
                }
                return false
            }
            Calendar.SUNDAY -> {
                if (intent.getBooleanExtra("SUNDAY", true)) {
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

    private fun setRepetitiveAlarm(alarmService: AlarmService, habitID: Int, message: String) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis += TimeUnit.HOURS.toMillis(24)
        }
        alarmService.setRepeating(cal.timeInMillis, habitID, message)
    }

    private fun setSnoozeAlarm(alarmService: AlarmService) {
        alarmService.setSnoozeAlarm()
    }

    private fun buildNotification(context: Context, title: String) {
        val intent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, ConstRequestCode.REQUEST_CODE_ACTIVITY, intent, 0)

        val builder = NotificationCompat.Builder(context, ConstIdChannel.HABIT_NOTIFICATION)
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

    private fun buildSnoozeNotification(context: Context, title: String, message: String, habitID: Int) {
        val intent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            ConstRequestCode.REQUEST_CODE_ACTIVITY,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val snoozeIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = Const.START_SNOOZE_ALARM_TIME
            putExtra(Const.EXTRA_EXACT_ALARM_TIME, 1L)
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(
                context,
                ConstRequestCode.REQUEST_CODE_ACTIVITY,
                snoozeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val builder = NotificationCompat.Builder(context, ConstIdChannel.HABIT_NOTIFICATION)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(context.getString(R.string.featureTrackhabit_content_notification, message))
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Delay 5m", snoozePendingIntent)
            .setAutoCancel(true)


        with(NotificationManagerCompat.from(context)) {
            notify(habitID, builder.build())

        }
    }

}
