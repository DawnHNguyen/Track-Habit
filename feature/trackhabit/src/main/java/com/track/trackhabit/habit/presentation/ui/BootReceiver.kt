package com.track.trackhabit.habit.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.track.common.base.utils.convertStringToCalender
import com.track.trackhabit.habit.domain.usecase.GetHabitUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var getHabitUseCase: GetHabitUseCase

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    val alarmService = AlarmService(context)
                    getHabitUseCase.getHabitValue().value?.forEach {
                        val timeNoti = SimpleDateFormat("HH:mm").format(it.time)
                        val cal = convertStringToCalender(timeNoti)
                        alarmService.setRepeating(cal.timeInMillis, it.habitId, it.title)
                    }
                }
            }
        }
    }
}