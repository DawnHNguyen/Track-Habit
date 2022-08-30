package com.track.trackhabit.habit.presentation.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.track.common.base.AppDispatchers
import com.track.common.base.data.remote.util.Resource
import com.track.common.base.utils.checkIsTodayDateFormatValue
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitUseCase: GetHabitUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val addInspectionUseCase: AddInspectionUseCase,
    private val updateInspectionUseCase: UpdateInspectionUseCase,
    private val dispatcher: AppDispatchers
) : ViewModel() {
    private val _habitList = MediatorLiveData<List<Habit>>()
    val habitList: LiveData<List<Habit>> get() = _habitList
    private var habitListSource: LiveData<Resource<List<Habit>>> = MutableLiveData()

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
                    _habitList.addSource(habitListSource){
                        //nothing to do in here
                    }
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

    fun updateInspection(habit: Habit) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                if (habit.performances.isNotEmpty() && checkIsTodayDateFormatValue(habit.performances.last().time)){
                    val performance = habit.performances.last()
                    updateInspectionUseCase(Inspection(performance.inspectionId, performance.time, !performance.check, performance.createAt, Date()), habit.habitId)
                }
                else addInspectionUseCase(Inspection(0, Date(), true, Date(), Date()), habit.habitId)
            }
        }
    }
}