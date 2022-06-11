package com.track.trackhabit.habit.presentation.ui

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.track.common.base.utils.convertStringToCalender
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.usecase.GetHabitByIdUseCase
import com.track.trackhabit.habit.presentation.constpackage.Const
import com.track.trackhabit.habit.presentation.constpackage.ConstIdChannel
import com.track.trackhabit.habit.presentation.constpackage.ConstRequestCode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@SuppressLint("LogNotTimber")
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var getHabitByIdUseCase: GetHabitByIdUseCase

    override fun onReceive(context: Context, intent: Intent) {

        createChannel(context)

        Log.d("onClickReceiver", "${intent.action} + $intent + ${intent.extras}")

        val alarmService = AlarmService(context)

        Log.d("CheckAction", "-action ${intent.action}")

        when (intent.action) {
            Const.START_SNOOZE_ALARM_TIME -> startSnoozeAlarmTime(context, intent, alarmService)

            Const.SET_SNOOZE_ALARM_TIME -> buildNotification(context, intent.getStringExtra(Const.HABIT_NAME).toString())

            Const.ACTION_SET_REPETITIVE_EXACT -> setRepeatingNotification(
                intent,
                context,
                alarmService
            )

            Const.SET_REMIND_SLEEPTIME -> setRemindSleepTime(context)
        }
    }

    private fun setRepetitiveAlarm(
        alarmService: AlarmService,
        habitID: Int,
        habitName: String,
        timeHabit: Date
    ) {
        val timeNoti = SimpleDateFormat("HH:mm").format(timeHabit)
        val cal = convertStringToCalender(timeNoti)
        cal.timeInMillis += TimeUnit.HOURS.toMillis(24)
        alarmService.setRepeating(cal.timeInMillis, habitID, habitName)
    }

    private fun setSnoozeAlarm(alarmService: AlarmService, habitID: Int, habitName: String) {
        alarmService.setSnoozeAlarm(habitID, habitName)
    }

    private fun buildNotification(context: Context, habitName: String) {
        val intent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, ConstRequestCode.REQUEST_CODE_ACTIVITY, intent, 0)

        val builder = NotificationCompat.Builder(context, ConstIdChannel.HABIT_NOTIFICATION)
            .setSmallIcon(R.mipmap.ic_trackhabit_logo)
            .setContentTitle(context.getString(R.string.featureTrackhabit_title_notification))
            .setContentText(context.getString(R.string.featureTrackhabit_content_notification, habitName))
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            notify(ConstIdChannel.ID_NOTIFICATION_SNOOZE, builder.build())
        }
    }

    private fun buildSnoozeNotification(
        context: Context,
        habitName: String,
        habitID: Int
    ) {
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
            putExtra(Const.HABIT_ID, habitID)
            putExtra(Const.HABIT_NAME, habitName)
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(
                context,
                ConstRequestCode.REQUEST_CODE_ACTIVITY,
                snoozeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val builder = NotificationCompat.Builder(context, ConstIdChannel.HABIT_NOTIFICATION)
            .setSmallIcon(R.mipmap.ic_trackhabit_logo)
            .setContentTitle(context.getString(R.string.featureTrackhabit_title_notification))
            .setContentText(
                context.getString(
                    R.string.featureTrackhabit_content_notification,
                    habitName
                )
            )
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .addAction(
                R.mipmap.ic_trackhabit_logo,
                context.getString(R.string.featureTrackhabit_delayNoti_button),
                snoozePendingIntent
            )
            .setAutoCancel(true)


        with(NotificationManagerCompat.from(context)) {
            notify(habitID, builder.build())

        }
    }

    private fun setRepeatingNotification(
        intent: Intent,
        context: Context,
        alarmService: AlarmService
    ) {
        val habitID = intent.getIntExtra(Const.HABIT_ID, 0)
        val habitName = intent.getStringExtra(Const.HABIT_NAME).toString()

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val habit: Habit = getHabitByIdUseCase.getHabitValue(habitID)
                val frequencyHabit = habit.frequency ?: "1111111"
                val timeHabit = habit.time
                Log.d("check_frequency", "--${frequencyHabit}")
                if (habit.isNotiToday()) {
                    buildSnoozeNotification(
                        context,
                        habitName,
                        habitID
                    )
                }

                setRepetitiveAlarm(
                    alarmService,
                    habitID,
                    habitName,
                    timeHabit
                )
            }
        }
    }

    private fun startSnoozeAlarmTime(context: Context, intent: Intent, alarmService: AlarmService) {
        setSnoozeAlarm(alarmService, intent.getIntExtra(Const.HABIT_ID, 0), intent.getStringExtra(Const.HABIT_NAME).toString())
        with(NotificationManagerCompat.from(context)) {
            cancel(intent.getIntExtra(Const.HABIT_ID, 0))
        }
    }

    private fun setRemindSleepTime(context: Context) {
        val builder = NotificationCompat.Builder(context, ConstIdChannel.REMIND_SLEEP_NOTIFICATION)
            .setSmallIcon(R.mipmap.ic_trackhabit_logo)
            .setContentTitle(context.getString(R.string.sleepNoti_title))
            .setContentText(context.getString(R.string.sleepNoti_content))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        with(NotificationManagerCompat.from(context)) {
            notify(ConstIdChannel.ID_REMIND_SLEEP_NOTIFICATION, builder.build())
        }
    }

}
