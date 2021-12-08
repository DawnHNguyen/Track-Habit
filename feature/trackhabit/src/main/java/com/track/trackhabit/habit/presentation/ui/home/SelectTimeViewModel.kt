package com.track.trackhabit.habit.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.track.common.base.AppDispatchers
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.usecase.AddHabitUseCase
import com.track.trackhabit.habit.domain.usecase.DeleteHabitUseCase
import com.track.trackhabit.habit.domain.usecase.GetHabitUseCase
import com.track.trackhabit.habit.domain.usecase.UpdateHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SelectTimeViewModel @Inject constructor(
    private val getHabitUseCase: GetHabitUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
): ViewModel()  {
    val nameHabit = MutableLiveData<String>()
    val descriptionHabit = MutableLiveData<String>()
    val timeHabit = MutableLiveData<String>()

    fun addHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                addHabitUseCase(habit)
            }
        }
    }
}