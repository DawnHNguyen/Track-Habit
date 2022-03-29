package com.track.trackhabit.habit.presentation.ui.sleeptime

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.track.common.base.AppDispatchers
import com.track.common.base.utils.convertStringToCalender
import com.track.trackhabit.habit.domain.entity.SleepDuration
import com.track.trackhabit.habit.domain.entity.Sleeptime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SleepTimeViewModel @Inject constructor(
    private val dispatchers: AppDispatchers
) : ViewModel() {
    private val _wakeTime = MutableLiveData("00:00")
    val wakeTime: LiveData<String>
        get() = _wakeTime

    val uiState = MutableLiveData(
        SleeptimeUIState(
            View.VISIBLE,
            View.INVISIBLE,
            View.INVISIBLE,
            View.INVISIBLE,
            false,
            true
        )
    )

    private val _remindTime = MutableLiveData<Long>()
    val remindTime: LiveData<Long>
        get() = _remindTime

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
            delay(20)
            uiState.value = uiState.value?.copy(
                conFirmWakeTimeVisibility = View.GONE,
                sleepTimeTitleVisibility = View.VISIBLE,
                confirmSleeptimeVisibility = View.VISIBLE,
                backButtonVisibility = View.VISIBLE,
                timePickerClickable = false
            )
        }
    }

    fun clearListSuggestSleeptime() {
        _sleepTimeList.value = listOf()
    }

    fun onBackUpdateVisibility() {
        uiState.value = uiState.value?.copy(
            conFirmWakeTimeVisibility = View.VISIBLE,
            sleepTimeTitleVisibility = View.INVISIBLE,
            confirmSleeptimeVisibility = View.INVISIBLE,
            backButtonVisibility = View.INVISIBLE,
            timePickerClickable = true
        )
    }

    fun resetTimepicker() {
        _wakeTime.value = "00:00"
    }

    fun setCofirmSleeptimeEnabled() {
        uiState.value = uiState.value?.copy(isEnabledConfirmSleeptimeButton = true)
    }

    fun setConfirmSleeptimeDisable() {
        uiState.value = uiState.value?.copy(isEnabledConfirmSleeptimeButton = false)
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
            if (sleepTimeLoop.isClicked) _remindTime.value = calNotiTime(sleepTimeLoop.sleepTime)
        }
        _sleepTimeList.value = sleepTimeList
    }

    private fun calNotiTime(timeSelect: String): Long {
        val presentTime = Calendar.getInstance().timeInMillis
        val cal = convertStringToCalender(timeSelect)
        val notiTime = cal.timeInMillis - 15 * 60 * 1000
        return if (notiTime >= presentTime - 15 * 60 * 1000) notiTime else notiTime + 24 * 3600 * 1000
    }
}