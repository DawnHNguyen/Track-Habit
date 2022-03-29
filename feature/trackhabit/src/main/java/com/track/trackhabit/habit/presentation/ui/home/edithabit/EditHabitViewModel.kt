package com.track.trackhabit.habit.presentation.ui.home.edithabit

import android.util.Log
import androidx.lifecycle.*
import com.track.common.base.AppDispatchers
import com.track.common.base.utils.convertStringToCalender
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.usecase.GetHabitByIdUseCase
import com.track.trackhabit.habit.domain.usecase.UpdateHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditHabitViewModel @Inject constructor(
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val dispatcher: AppDispatchers
) : ViewModel() {

    private val _habit = MediatorLiveData<Habit>()
    val habit: LiveData<Habit> get() = _habit
    private var habitSource: LiveData<Habit> = MutableLiveData()

    var monday = MutableLiveData<Boolean>(true)
    var tuesday = MutableLiveData<Boolean>(true)
    var wednesday = MutableLiveData<Boolean>(true)
    var thursday = MutableLiveData<Boolean>(true)
    var friday = MutableLiveData<Boolean>(true)
    var saturday = MutableLiveData<Boolean>(true)
    var sunday = MutableLiveData<Boolean>(true)
    val timeHabit = MutableLiveData<String>()



    fun getHabit(id: Int) {
        viewModelScope.launch(dispatcher.main) {
            _habit.removeSource(habitSource)
            withContext(dispatcher.io) {
                habitSource = getHabitByIdUseCase(id)
            }
            try {
                _habit.addSource(habitSource) {
                    _habit.value = it
                    _habit.value?.let {
                        monday.value = it.frequency!![0] == '1'
                        tuesday.value = it.frequency[1] == '1'
                        wednesday.value = it.frequency[2] == '1'
                        thursday.value = it.frequency[3] == '1'
                        friday.value = it.frequency[4] == '1'
                        saturday.value = it.frequency[5] == '1'
                        sunday.value = it.frequency[6] == '1'
                        timeHabit.value = SimpleDateFormat("HH:mm").format(it.time)
                    }
                }
            } catch (e: IllegalArgumentException) {
                Log.d("EditHabitViewModel", e.toString())
            }
        }
    }


    fun updatehabit(habit: Habit) {
        viewModelScope.launch(dispatcher.main) {
            withContext(dispatcher.io) {
                updateHabitUseCase(habit)
                Timber.d("-->${habit}_viewmodel")
                Timber.d("successUpdate")
            }
        }
    }

    private fun convertBooleanToInt(bool: MutableLiveData<Boolean>): Int {
        if (bool.value == null) return 1
        if (bool.value == true) {
            return 1
        }
        return 0
    }

    fun getFrequency(): String {
        return "" + convertBooleanToInt(
            monday
        ) + convertBooleanToInt(
            tuesday
        ) + convertBooleanToInt(
            wednesday
        ) + convertBooleanToInt(
            thursday
        ) + convertBooleanToInt(
            friday
        ) + convertBooleanToInt(
            saturday
        ) + convertBooleanToInt(
            sunday
        )
    }

    fun getNewHabitTime(): Date {
        val timeSelect = timeHabit.value.toString()
        val presentTime = Calendar.getInstance().time
        val cal = convertStringToCalender(timeSelect)

        return if (cal.time >= presentTime) cal.time
        else {
            cal.add(Calendar.DATE, 1)
            cal.time
        }
    }
}