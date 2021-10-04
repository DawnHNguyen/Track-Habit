package com.track.trackhabit.data.repository

import com.track.common.base.LocalDtoListToDomainModelListMapper
import com.track.trackhabit.habit.data.local.dao.HabitDao
import com.track.trackhabit.habit.data.local.dao.InspectionDao
import com.track.trackhabit.habit.data.local.dao.UserDao
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.User
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository

class TrackHabitRepositoryImpl(private val userDao: UserDao, private val habitDao: HabitDao, private val inspectionDao: InspectionDao): TrackHabitRepository {
    override suspend fun addUser(user: User) {
        userDao.insertUser(user.toLocalDto())
    }

    override suspend fun addHabit(habit: Habit) {
        habitDao.insertHabit(habit.toLocalDto())
    }

    override suspend fun addInspection(inspection: Inspection) {
        inspectionDao.insertInspection(inspection.toLocalDto())
    }

    override suspend fun getHabit(): List<Habit> =
        LocalDtoListToDomainModelListMapper().map(habitDao.getHabit()) as List<Habit>

    override suspend fun getInspection(): List<Inspection> =
        LocalDtoListToDomainModelListMapper().map(inspectionDao.getInspection()) as List<Inspection>

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toLocalDto())
    }

    override suspend fun deleteHabit(id: Int) {
        habitDao.deleteHabit(id)
    }


}