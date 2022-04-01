package com.track.trackhabit.habit.presentation.ui.home.habitinfo

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.track.common.base.AppDispatchers
import com.track.common.base.utils.convertStringToCalender
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
class HabitInfoViewModel @Inject constructor(
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val dispatcher: AppDispatchers
) : ViewModel() {
    private var idHabit: Int = 0
    val name = MutableLiveData<String>()
    val time = MutableLiveData<String>()

    private val _habit = MediatorLiveData<Habit>()
    val habit: LiveData<Habit> get() = _habit
    private var habitSource: LiveData<Habit> = MutableLiveData()

    private val _nameErrorVisibility = MutableLiveData(View.GONE)
    val nameErrorVisibility: LiveData<Int>
        get() = _nameErrorVisibility

    private val _inputValidity = MutableLiveData(false)
    val inputValidity: LiveData<Boolean>
        get() = _inputValidity

    var monday = MutableLiveData(true)
    var tuesday = MutableLiveData(true)
    var wednesday = MutableLiveData(true)
    var thursday = MutableLiveData(true)
    var friday = MutableLiveData(true)
    var saturday = MutableLiveData(true)
    var sunday = MutableLiveData(true)

    fun addHabit() {
        if (name.value.isNullOrBlank()) {
            _nameErrorVisibility.value = View.VISIBLE
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    addHabitUseCase(
                        Habit(
                            0,
                            name.value.toString(),
                            formatTimeFromStringToDate(),
                            emptyList(),
                            getFrequency()
                        )
                    )
                }
            }
            _inputValidity.value = true
        }
    }

    fun getHabit() {
        viewModelScope.launch(dispatcher.main) {
            _habit.removeSource(habitSource)
            withContext(dispatcher.io) {
                habitSource = getHabitByIdUseCase(idHabit)
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
                        time.value = SimpleDateFormat("HH:mm").format(it.time)
                        name.value = it.title
                        Log.d("Check_habit", "Check name habit: ${name}")
                    }
                }
            } catch (e: IllegalArgumentException) {
                Log.d("HabitInfoViewModel", e.toString())
            }
        }
    }


     fun updateHabit() {
        if (name.value.isNullOrBlank()) {
            _nameErrorVisibility.value = View.VISIBLE
        } else {
            viewModelScope.launch(dispatcher.main) {
                withContext(dispatcher.io) {
                    updateHabitUseCase(Habit(
                        idHabit,
                        name.value.toString(),
                        formatTimeFromStringToDate(),
                        habit.value!!.performances,
                        getFrequency()
                    ))
                    Timber.d("-->${habit}_viewmodel")
                    Timber.d("successUpdate")
                }
            }
            _inputValidity.value = true
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

    fun formatTimeFromStringToDate(): Date {
        val timeSelect = time.value.toString()
        val presentTime = Calendar.getInstance().time
        val cal = convertStringToCalender(timeSelect)

        return if (cal.time >= presentTime) cal.time
        else {
            cal.add(Calendar.DATE, 1)
            cal.time
        }
    }

    fun isEditCase(): Boolean{
        return idHabit != -1
    }

    fun getHabitId(id: Int){
        idHabit = id
    }
}