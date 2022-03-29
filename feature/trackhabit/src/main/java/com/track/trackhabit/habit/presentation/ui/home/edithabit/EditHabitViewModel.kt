package com.track.trackhabit.habit.presentation.ui.home.edithabit

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.track.common.base.AppDispatchers
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.usecase.AddHabitUseCase
import com.track.trackhabit.habit.domain.usecase.GetHabitByIdUseCase
import com.track.trackhabit.habit.domain.usecase.UpdateHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val addHabitUseCase: AddHabitUseCase,
    private val dispatcher: AppDispatchers
) : ViewModel() {
    val nameHabit = MutableLiveData<String>("chay bo 5km/ngay")
    val timeHabit = MutableLiveData<String>()

    private val _habit = MediatorLiveData<Habit>()
    val habit: LiveData<Habit> get() = _habit
    private var habitSource: LiveData<Habit> = MutableLiveData()

    private val _nameErrorVisibility = MutableLiveData(View.GONE)
    val nameErrorVisibility: LiveData<Int>
        get() = _nameErrorVisibility

    private val _inputValidity = MutableLiveData<Boolean>(false)
    val inputValidity: LiveData<Boolean>
        get() = _inputValidity

    var monday = MutableLiveData<Boolean>(true)
    var tuesday = MutableLiveData<Boolean>(true)
    var wednesday = MutableLiveData<Boolean>(true)
    var thursday = MutableLiveData<Boolean>(true)
    var friday = MutableLiveData<Boolean>(true)
    var saturday = MutableLiveData<Boolean>(true)
    var sunday = MutableLiveData<Boolean>(true)
    var someday =MediatorLiveData<Boolean>()

    fun addHabit() {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                addHabitUseCase(
                    Habit(
                        0,
                        nameHabit.value.toString(),
                        changeToTime(),
                        emptyList(),
                        getFrequency()
                    )
                )
            }
        }
    }

    fun getHabit(id: Int) {
        viewModelScope.launch(dispatcher.main) {
            _habit.removeSource(habitSource)
            withContext(dispatcher.io) {
                habitSource = getHabitByIdUseCase(id)
            }
            try {
                _habit.addSource(habitSource) {
                    _habit.value = it
                    it?.let {
                        monday.value = it.frequency!![0] == '1'
                        tuesday.value = it.frequency[1] == '1'
                        wednesday.value = it.frequency[2] == '1'
                        thursday.value = it.frequency[3] == '1'
                        friday.value = it.frequency[4] == '1'
                        saturday.value = it.frequency[5] == '1'
                        sunday.value = it.frequency[6] == '1'
                        timeHabit.value = SimpleDateFormat("HH:mm").format(it.time)
                        nameHabit.value = it.title
                        Log.d("Check_habit","Check name habit: ${nameHabit}")
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

    fun changeToTime(): Date {
        val timeSelect = timeHabit.value.toString()
        val arr = timeSelect.split(':')
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, arr[0].toInt())
        cal.set(Calendar.MINUTE, arr[1].toInt())
        return cal.time
    }

    fun handleInputAddCases() {
        if (nameHabit.value.isNullOrBlank() ) {
            _nameErrorVisibility.value = View.VISIBLE
        } else {
            addHabit()
            _inputValidity.value = true
        }
    }

    fun handleInputEditCases(habit: Habit) {
        if (nameHabit.value.isNullOrBlank() ) {
            _nameErrorVisibility.value = View.VISIBLE
        } else {
            updatehabit(habit)
            _inputValidity.value = true
        }
    }
}