package com.track.trackhabit.habit.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.track.common.base.utils.convertStringToCalender
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.usecase.GetHabitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import javax.inject.Inject

class BootReceiver: BroadcastReceiver() {
    @Inject
    lateinit var getHabitUseCase: GetHabitUseCase

    private var habitList: List<Habit>? = listOf()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            getHabit()
            val alarmService = AlarmService(context)
            habitList?.forEach {
                val timeNoti = SimpleDateFormat("HH:mm").format(it.time)
                val cal = convertStringToCalender(timeNoti)
                alarmService.setRepeating(cal.timeInMillis, it.habitId, it.title)
            }
        }

    }

    private fun getHabit() {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                habitList = getHabitUseCase().value
            }
        }
    }
}