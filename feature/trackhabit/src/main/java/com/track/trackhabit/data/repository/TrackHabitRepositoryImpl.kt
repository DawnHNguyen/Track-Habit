package com.track.trackhabit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    override suspend fun addHabit(habit: Habit) {
        habitDao.insertHabit(habit.toLocalDto())
    }

    override suspend fun addInspection(inspection: Inspection) {
        inspectionDao.insertInspection(inspection.toLocalDto())
    }

    override suspend fun getHabit(): LiveData<List<Habit>> {
        val liveData = MutableLiveData<List<Habit>>()
        liveData.postValue(habitDao.getHabit().map {
            it.mapToDomainModel()
        })
        return liveData
    }

    override suspend fun getInspection(): LiveData<List<Inspection>> {
        val liveData = MutableLiveData<List<Inspection>>()
        liveData.postValue(inspectionDao.getInspection().map {
            it.mapToDomainModel()
        })
        return liveData
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toLocalDto())
    }

    override suspend fun deleteHabit(id: Int) {
        habitDao.deleteHabit(id)
    }
}