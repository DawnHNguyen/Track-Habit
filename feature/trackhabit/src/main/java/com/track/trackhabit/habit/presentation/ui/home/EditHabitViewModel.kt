package com.track.trackhabit.habit.presentation.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.track.common.base.AppDispatchers
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.usecase.GetHabitByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class EditHabitViewModel @Inject constructor(
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val dispatcher: AppDispatchers
): ViewModel() {

    private val _habit = MediatorLiveData<Habit>()
    val habit: LiveData<Habit> get() = _habit
    private var habitSource: LiveData<Habit> = MutableLiveData()

    fun getHabit(id: Int){
        viewModelScope.launch(dispatcher.main) {
            _habit.removeSource(habitSource)
            withContext(dispatcher.io){
                habitSource = getHabitByIdUseCase(id)
            }
            try {
                _habit.addSource(habitSource){
                    _habit.value = it
                }

            }catch (e: IllegalArgumentException){
                Log.d("EditHabitViewModel", e.toString())
            }

        }
    }
}