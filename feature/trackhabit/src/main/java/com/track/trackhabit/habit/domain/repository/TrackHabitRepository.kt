package com.track.trackhabit.habit.domain.repository

import androidx.lifecycle.LiveData
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.User

interface TrackHabitRepository {
    suspend fun getHabit(): LiveData<List<Habit>>
    suspend fun addHabit(habit: Habit): Long
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(id: Int)
    fun getHabitById(id: Int): LiveData<Habit>
    suspend fun getHabitValueById(id: Int): Habit
    fun getHabitValues(): LiveData<List<Habit>>
}