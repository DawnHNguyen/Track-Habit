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
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.usecase.GetHabitByIdUseCase
import com.track.trackhabit.habit.presentation.constpackage.Const
import com.track.trackhabit.habit.presentation.constpackage.ConstIdChannel
import com.track.trackhabit.habit.presentation.constpackage.ConstRequestCode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver() : BroadcastReceiver() {
    @Inject lateinit var getHabitByIdUseCase: GetHabitByIdUseCase

    override fun onReceive(context: Context, intent: Intent) {

        createChannel(context)
        val timeInMillis = intent.getLongExtra(Const.EXTRA_EXACT_ALARM_TIME, 0L)

        Log.d("onClickReceiver", "${intent.action} + ${intent} + ${intent.extras}")
        var alarmService = AlarmService(context)
        Log.d("CheckAction", "-action ${intent.action}")

        when (intent.action) {
            Const.START_SNOOZE_ALARM_TIME -> startSnoozeAlarmTime(context, intent, alarmService)

            Const.SET_SNOOZE_ALARM_TIME -> buildNotification(context, "Set Snooze Time")

            Const.ACTION_SET_REPETITIVE_EXACT -> setRepeatingNotification(intent, context, timeInMillis, alarmService)

            Const.SET_REMIND_SLEEPTIME -> setRemindSleepTime(context)
        }
    }

    suspend fun getHabitFrequency(intent: Intent) : String{
        val habit: Habit = getHabitByIdUseCase.getHabitValue(intent.getIntExtra(Const.HABIT_ID,0))
        Log.d("check_habit","${habit} -- ${intent.getIntExtra(Const.HABIT_ID,0)}")
        return habit.frequency ?: "1111111"
    }

    private fun isToday(frequency: String): Boolean {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val notifyMonday = frequency[0] == '1'
        val notifyTuesday = frequency[1] == '1'
        val notifyWednesday = frequency[2] == '1'
        val notifyThursday = frequency[3] == '1'
        val notifyFriday = frequency[4] == '1'
        val notifySaturday = frequency[5] == '1'
        val notifySunday = frequency[6] == '1'
        return when (today) {
            Calendar.MONDAY -> notifyMonday

            Calendar.TUESDAY -> notifyTuesday

            Calendar.WEDNESDAY -> notifyWednesday

            Calendar.THURSDAY -> notifyThursday

            Calendar.FRIDAY -> notifyFriday

            Calendar.SATURDAY -> notifySaturday

            Calendar.SUNDAY -> notifySunday

            else -> true
        }
    }

    private fun convertDate(timeInMillis: Long): String {
        return SimpleDateFormat("dd/MM/yyyy hh:mm").format(Date(timeInMillis))
    }

    private fun setRepetitiveAlarm(alarmService: AlarmService, habitID: Int, habitName: String) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis += TimeUnit.HOURS.toMillis(24)
        }
        alarmService.setRepeating(cal.timeInMillis, habitID, habitName)
    }

    private fun setSnoozeAlarm(alarmService: AlarmService, habitID: Int) {
        alarmService.setSnoozeAlarm(habitID)
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

    private fun buildSnoozeNotification(
        context: Context,
        title: String,
        message: String,
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

    private fun setRepeatingNotification(intent: Intent, context: Context, timeInMillis: Long, alarmService: AlarmService){
        val habitID = intent.getIntExtra(Const.HABIT_ID, 0)
        val habitName = intent.getStringExtra(Const.HABIT_NAME).toString()

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                val frequencyHabit = getHabitFrequency(intent)
                Log.d("check_frequency","--${frequencyHabit}")
                if (isToday(frequencyHabit)) {
                    buildSnoozeNotification(
                        context,
                        context.getString(R.string.featureTrackhabit_title_notification),
                        convertDate(timeInMillis),
                        habitID
                    )
                }
            }
        }

        setRepetitiveAlarm(
            alarmService,
            habitID,
            habitName)
    }

    private fun startSnoozeAlarmTime(context: Context, intent: Intent, alarmService: AlarmService){
        setSnoozeAlarm(alarmService,  intent.getIntExtra(Const.HABIT_ID, 0))
        with(NotificationManagerCompat.from(context)) {
            cancel(ConstIdChannel.ID_NOTIFICATION_1)
        }
    }

    private fun setRemindSleepTime(context: Context) {
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
