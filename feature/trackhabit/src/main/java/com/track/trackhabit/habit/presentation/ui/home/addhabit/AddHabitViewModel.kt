package com.track.trackhabit.habit.presentation.ui.home.addhabit

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.usecase.AddHabitUseCase
import com.track.trackhabit.habit.domain.usecase.DeleteHabitUseCase
import com.track.trackhabit.habit.domain.usecase.GetHabitUseCase
import com.track.trackhabit.habit.domain.usecase.UpdateHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddHabitViewModel @Inject constructor(
    private val getHabitUseCase: GetHabitUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
) : ViewModel() {
    val nameHabit = MutableLiveData<String>()
    val descriptionHabit = MutableLiveData<String>()
    val timeHabit = MutableLiveData<String>()

    private val _nameErrorVisibility = MutableLiveData(View.GONE)
    val nameErrorVisibility: LiveData<Int>
        get() = _nameErrorVisibility

    private val _descriptionErrorVisibility = MutableLiveData(View.GONE)
    val descriptionErrorVisibility: LiveData<Int>
        get() = _descriptionErrorVisibility

    private val _inputValidity = MutableLiveData<Boolean>(false)
    val inputValidity: LiveData<Boolean>
        get() = _inputValidity

    var monday: Boolean = true
    var tuesday: Boolean = true
    var wednesday: Boolean = true
    var thursday: Boolean = true
    var friday: Boolean = true
    var saturday: Boolean = true
    var sunday: Boolean = true

    fun addHabit() {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                addHabitUseCase(
                    Habit(
                        0,
                        nameHabit.value.toString(),
                        descriptionHabit.value.toString(),
                        changeToTime(timeHabit.value!!),
                        emptyList(),
                        getFrequency()
                    )
                )
            }
        }
    }

    private fun getFrequency(): String {
        return "" + monday.compareTo(false) + tuesday.compareTo(false) + wednesday.compareTo(false) + thursday.compareTo(
            false
        ) + friday.compareTo(false) + saturday.compareTo(false) + sunday.compareTo(false)
    }

    private fun changeToTime(timeSelect: String): Date {
        val arr = timeSelect.split(':')
        val cal = Calendar.getInstance()
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        cal.set(Calendar.HOUR_OF_DAY, arr[0].toInt())
        cal.set(Calendar.MINUTE, arr[1].toInt())
        return cal.time
    }

    fun handleDifferentInputCases() {
        if (nameHabit.value.isNullOrBlank() || descriptionHabit.value.isNullOrBlank()) {
            if (nameHabit.value.isNullOrBlank()) _nameErrorVisibility.value = View.VISIBLE
            if (descriptionHabit.value.isNullOrBlank()) _descriptionErrorVisibility.value =
                View.VISIBLE
        } else {
            Log.d(
                "check_databinding",
                "${nameHabit.value} + ${descriptionHabit.value} + ${timeHabit.value}"
            )
            addHabit()
            _inputValidity.value = true
        }
    }
}