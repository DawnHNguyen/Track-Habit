package com.track.trackhabit.habit.domain.repository

import androidx.lifecycle.LiveData
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.User

interface TrackHabitRepository {
    suspend fun addUser(user: User)
    suspend fun addHabit(habit: Habit)
    suspend fun addInspection(inspection: Inspection)
    suspend fun getHabit(): LiveData<List<Habit>>
    suspend fun getInspection(): LiveData<List<Inspection>>
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(id: Int)
}