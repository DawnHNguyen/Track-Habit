package com.track.trackhabit.habit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.track.trackhabit.habit.data.local.dao.HabitDao
import com.track.trackhabit.habit.data.local.dao.InspectionDao
import com.track.trackhabit.habit.data.local.dao.UserDao
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.User
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import javax.inject.Inject

class TrackHabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao
): TrackHabitRepository {

    override suspend fun addHabit(habit: Habit): Long {
        return habitDao.insertHabit(habit.toLocalDto())
    }

    override suspend fun getHabit(): LiveData<List<Habit>> =
        Transformations.map(habitDao.getHabit()) { list ->
            list.map {
                val listInspection = ArrayList<Inspection>()
                it.listInspection.forEach { item ->
                    listInspection.add(item.mapToDomainModel())
                }
                val habit = it.habit.mapToDomainModel()
                habit.performances = listInspection
                habit
            }
        }

    override fun getHabitById(id: Int): LiveData<Habit> =
        habitDao.getHabitById(id).map { it ->
            it.mapToDomainModel()
        }



    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toLocalDto())
    }

    override suspend fun deleteHabit(id: Int) {
        habitDao.deleteHabit(id)
    }

    override suspend fun getHabitValueById(id: Int): Habit =
        habitDao.getHabitValueById(id).mapToDomainModel()

}