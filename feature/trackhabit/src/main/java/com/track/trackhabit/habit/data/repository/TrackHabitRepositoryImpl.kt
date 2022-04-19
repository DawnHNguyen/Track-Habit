package com.track.trackhabit.habit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.track.trackhabit.habit.data.local.dao.HabitDao
import com.track.trackhabit.habit.data.local.dao.InspectionDao
import com.track.trackhabit.habit.data.local.dao.UserDao
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.User
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import javax.inject.Inject

class TrackHabitRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val habitDao: HabitDao,
    private val inspectionDao: InspectionDao
): TrackHabitRepository {

    override suspend fun addUser(user: User) {
        userDao.insertUser(user.toLocalDto())
    }

    override suspend fun addHabit(habit: Habit): Long {
        return habitDao.insertHabit(habit.toLocalDto())
    }

    override suspend fun addInspection(inspection: Inspection) {
        inspectionDao.insertInspection(inspection.toLocalDto())
    }

    override fun getHabit(): LiveData<List<Habit>> =
        Transformations.map(habitDao.getHabit()) { list ->
            list.map { it.mapToDomainModel() }
        }

    override fun getHabitById(id: Int): LiveData<Habit> =
        Transformations.map(habitDao.getHabitById(id)) { it ->
            it.mapToDomainModel()
        }

    override fun getInspectionByHabit(id: Int): LiveData<List<Inspection>> =
        Transformations.map(inspectionDao.getInspectionByHabit(id)) { it ->
            it.listInspection.map { it.mapToDomainModel() }
        }

    override fun getInspection(): LiveData<List<Inspection>> =
        Transformations.map(inspectionDao.getInspection()) { list ->
            list.map { it.mapToDomainModel() }
        }

    override suspend fun getHabitValueById(id: Int): Habit = habitDao.getHabitValueById(id).mapToDomainModel()

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toLocalDto())
    }

    override suspend fun deleteHabit(id: Int) {
        habitDao.deleteHabit(id)
    }
}