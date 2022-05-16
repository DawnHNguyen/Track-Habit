package com.track.trackhabit.habit.presentation.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.track.common.base.AppDispatchers
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitUseCase: GetHabitUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val addInspectionUseCase: AddInspectionUseCase,
    private val dispatcher: AppDispatchers
) : ViewModel() {
    private val _habitList = MediatorLiveData<List<Habit>>()
    val habitList: LiveData<List<Habit>> get() = _habitList
    private var habitListSource: LiveData<List<Habit>> = MutableLiveData()

    private var idHabit: Int = 0

    init {
        getHabit()
    }

    private fun getHabit() {
        viewModelScope.launch(dispatcher.main) {
            _habitList.removeSource(habitListSource)
            withContext(dispatcher.io) {
                habitListSource = getHabitUseCase()
            }
            try {
                _habitList.addSource(habitListSource) {
                    _habitList.value = it
                }
            } catch (e: IllegalArgumentException) {
                Log.d("HomeViewModel", e.toString())
            }
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch(dispatcher.main) {
            withContext(dispatcher.io) {
                deleteHabitUseCase(habit.habitId, habit.title)
            }
        }
    }

    fun addInspection() {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                addInspectionUseCase(Inspection(0, Date(), true), idHabit)
                Log.d("check_update_inspection", "$idHabit")
            }
        }
    }

    fun getHabitId(id: Int){
        idHabit = id
    }
}