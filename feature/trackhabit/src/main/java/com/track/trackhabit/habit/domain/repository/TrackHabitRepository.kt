package com.track.trackhabit.habit.domain.repository

import androidx.lifecycle.LiveData
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.User

interface TrackHabitRepository {
    suspend fun addUser(user: User)
    suspend fun addHabit(habit: Habit): Long
    suspend fun addInspectionToHabit(inspection: Inspection, habitId: Int)
    fun getHabit(): LiveData<List<Habit>>
    fun getHabitById(id: Int): LiveData<Habit>
    fun getInspectionByHabitId(id: Int): LiveData<List<Inspection>>
    fun getInspection(): LiveData<List<Inspection>>
    suspend fun getHabitValueById(id: Int): Habit
    suspend fun getHabitsValue(): List<Habit>
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(id: Int)
}