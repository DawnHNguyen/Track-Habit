package com.track.trackhabit.habit.presentation.ui.sleeptime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.track.common.base.AppDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SleepTimeViewModel @Inject constructor(
    private val dispatchers: AppDispatchers
) : ViewModel() {
    private val _wakeTime =  MutableLiveData("00:00")
    val wakeTime: LiveData<String>
    get() = _wakeTime
    fun calSleepTime(wakeTime: String, durationHour: Int, durationMin: Int): String {
        var sleepTime: String
        val arrayWake = wakeTime.split(':').run {
            IntArray(size) {
                get(it).toInt()
            }
        }
        val wakeMin = arrayWake[1]
        var wakeHour = arrayWake[0]

        val sleepMin: Int
        if (wakeMin < durationMin) {
            sleepMin = 60 - (durationMin - wakeMin)
            wakeHour -= 1
        } else sleepMin = wakeMin - durationMin

        val sleepHour: Int = if (wakeHour < durationHour) 24 - (durationHour - wakeHour) else wakeHour - durationHour
        sleepTime = if (sleepHour < 10) "0$sleepHour" else "$sleepHour"
        sleepTime = if (sleepMin < 10) "$sleepTime:0$sleepMin" else "$sleepTime:$sleepMin"
        return sleepTime
    }

    fun setWakeTime(wakeTime: String) {
        _wakeTime.value = wakeTime
    }
}