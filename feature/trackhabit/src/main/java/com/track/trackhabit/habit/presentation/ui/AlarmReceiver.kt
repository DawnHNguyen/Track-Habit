package com.track.trackhabit.habit.presentation.ui

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import com.track.trackhabit.habit.domain.usecase.GetHabitByIdUseCase
import com.track.trackhabit.habit.presentation.constpackage.Const
import com.track.trackhabit.habit.presentation.constpackage.ConstIdChannel
import com.track.trackhabit.habit.presentation.constpackage.ConstRequestCode
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver() : BroadcastReceiver() {
    @Inject lateinit var getHabitByIdUseCase: GetHabitByIdUseCase

    override fun onReceive(context: Context, intent: Intent) {

        createChannel(context)
        goAsync()
        val timeInMillis = intent.getLongExtra(Const.EXTRA_EXACT_ALARM_TIME, 0L)

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

                runBlocking {
                    val frequencyHabit = getHabitFrequency(intent)
                    Log.d("check_frequency","--${frequencyHabit}")
                    if (isToday(frequencyHabit)) {
                        buildSnoozeNotification(
                            context,
                            context.getString(R.string.featureTrackhabit_title_notification),
                            convertDate(timeInMillis),
                            intent.getIntExtra(Const.HABIT_ID, 0)
                        )
                    }
                }

                setRepetitiveAlarm(alarmService, intent.getIntExtra("habitId", 0))
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

    suspend fun getHabitFrequency(intent: Intent) : String{
        val habit: LiveData<Habit> = getHabitByIdUseCase(intent.getIntExtra(Const.HABIT_ID,0))
        Log.d("check_habit","${habit.value} -- ${intent.getIntExtra(Const.HABIT_ID,0)}")
        return habit.value?.frequency ?: "1111111"
    }

    private fun isToday(frequency: String): Boolean {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

        when (today) {
            Calendar.MONDAY -> {
                if (frequency[0]=='1') {
                    return true
                }
                return false
            }
            Calendar.TUESDAY -> {

                if (frequency[1]=='1') {
                    return true
                }
                return false
            }
            Calendar.WEDNESDAY -> {
                if (frequency[2]=='1') {
                    return true
                }
                return false
            }
            Calendar.THURSDAY -> {
                if (frequency[3]=='1') {
                    return true
                }
                return false
            }
            Calendar.FRIDAY -> {
                if (frequency[4]=='1') {
                    return true
                }
                return false
            }
            Calendar.SATURDAY -> {
                if (frequency[5]=='1') {
                    return true
                }
                return false
            }
            Calendar.SUNDAY -> {
                if (frequency[6]=='1') {
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

    private fun setRepetitiveAlarm(alarmService: AlarmService, habitID: Int) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis += TimeUnit.HOURS.toMillis(24)
        }
        alarmService.setRepeating(cal.timeInMillis, habitID)
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

        val builder = NotificationCompat.Builder(context, ConstIdChannel.NOTIFICATION_1)
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
