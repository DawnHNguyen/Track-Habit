package com.track.trackhabit.habit.presentation.ui.sleeptime

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.track.common.base.AppDispatchers
import com.track.trackhabit.habit.domain.entity.SleepDuration
import com.track.trackhabit.habit.domain.entity.Sleeptime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepTimeViewModel @Inject constructor(
    private val dispatchers: AppDispatchers
) : ViewModel() {
    private val _wakeTime = MutableLiveData("00:00")
    val wakeTime: LiveData<String>
        get() = _wakeTime

    private val _conFirmWakeTimeVisibility = MutableLiveData(View.VISIBLE)
    val conFirmWakeTimeVisibility: LiveData<Int>
        get() = _conFirmWakeTimeVisibility

    private val _sleepTimeTilteVisibility = MutableLiveData(View.INVISIBLE)
    val sleepTimeTitleVisibility: LiveData<Int>
        get() = _sleepTimeTilteVisibility

    private val _confirmSleeptimeVisibility = MutableLiveData(View.INVISIBLE)
    val confirmSleeptimeVisibility: LiveData<Int>
        get() = _confirmSleeptimeVisibility

    private val _backButtonVisibility = MutableLiveData(View.INVISIBLE)
    val backButtonVisibility: LiveData<Int>
        get() = _backButtonVisibility

    private val _isEnabledConfirmSleeptimeButton = MutableLiveData(false)
    val isEnabledConfirmSleeptime: LiveData<Boolean>
        get() = _isEnabledConfirmSleeptimeButton

    private fun calSleepTime(wakeTime: String, durationHour: Int, durationMin: Int): String {
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

        val sleepHour: Int =
            if (wakeHour < durationHour) 24 - (durationHour - wakeHour) else wakeHour - durationHour
        sleepTime = if (sleepHour < 10) "0$sleepHour" else "$sleepHour"
        sleepTime = if (sleepMin < 10) "$sleepTime:0$sleepMin" else "$sleepTime:$sleepMin"
        return sleepTime
    }

    private val _sleepTimeList = MutableLiveData<List<Sleeptime>>()
    val sleepTimeList: LiveData<List<Sleeptime>>
        get() = _sleepTimeList

    fun addListSleepTime() {
        val sleepTimeList = mutableListOf<Sleeptime>()
        SleepDuration.values().forEachIndexed { index: Int, it: SleepDuration ->
            val sleepTimeLoop = Sleeptime(
                index,
                calSleepTime(
                    wakeTime.value ?: "00:00",
                    it.sleepDurationHour,
                    it.sleepDurationMin
                ),
                it.sleepDuration,
                it.loop
            )
            sleepTimeList.add(sleepTimeLoop)
        }
        _sleepTimeList.value = sleepTimeList
    }

    fun setWakeTime(wakeTime: String) {
        _wakeTime.value = wakeTime
    }

    fun onConfirmWaketimeUpdateVisibility() {
        viewModelScope.launch {
            delay(40)
            _conFirmWakeTimeVisibility.value = View.GONE
            _sleepTimeTilteVisibility.value = View.VISIBLE
            _confirmSleeptimeVisibility.value = View.VISIBLE
            _backButtonVisibility.value = View.VISIBLE
        }
    }

    fun clearListSuggestSleeptime() {
        _sleepTimeList.value = listOf()
    }

    fun onBackUpdateVisibility() {
        _conFirmWakeTimeVisibility.value = View.VISIBLE
        _sleepTimeTilteVisibility.value = View.INVISIBLE
        _confirmSleeptimeVisibility.value = View.INVISIBLE
        _backButtonVisibility.value = View.INVISIBLE
    }

    fun resetTimepicker() {
        _wakeTime.value = "00:00"
    }

    fun setCofirmSleeptimeEnabled() {
        _isEnabledConfirmSleeptimeButton.value = true
    }

    fun setConfirmSleeptimeDisable(){
        _isEnabledConfirmSleeptimeButton.value = false
    }

    fun onClickItem(clickedLoop: Int) {
        val sleepTimeList = mutableListOf<Sleeptime>()
        SleepDuration.values().forEachIndexed { index: Int, it: SleepDuration ->
            val sleepTimeLoop = Sleeptime(
                index,
                calSleepTime(
                    wakeTime.value ?: "00:00",
                    it.sleepDurationHour,
                    it.sleepDurationMin
                ),
                it.sleepDuration,
                it.loop,
                clickedLoop == it.loop
            )
            sleepTimeList.add(sleepTimeLoop)
        }
        _sleepTimeList.value = sleepTimeList
    }
}