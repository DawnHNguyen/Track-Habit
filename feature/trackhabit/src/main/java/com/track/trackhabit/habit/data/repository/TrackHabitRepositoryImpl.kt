package com.track.trackhabit.habit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.track.trackhabit.habit.data.local.dao.HabitDao
import com.track.trackhabit.habit.data.remote.services.HabitDataSource
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import javax.inject.Inject

class TrackHabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private val habitDataSource: HabitDataSource
): TrackHabitRepository {

    override suspend fun addHabit(habit: Habit): Long {
//        Đây là logic để addHabit qua API, không uncomment ở đây vì logic navigate sẽ làm quá trình call API bị Cancel
//        val reminderDays = mutableListOf<Int>()
//        habit.frequency?.forEachIndexed { index, c ->
//            if(c == '1') reminderDays.add(index)
//        }
//        val reminderTime = SimpleDateFormat("HH:mm").format(habit.time.time)
//        val addHabitRequest = AddHabitRequest(null, reminderDays, reminderTime, habit.title)
//        habitDataSource.addHabit(addHabitRequest)
        return habitDao.insertHabit(habit.toLocalDto())
    }

    override suspend fun getHabit(): LiveData<List<Habit>> =
        Transformations.map(habitDao.getHabit()) { list ->
            list.map {
                val inspections = it.listInspection.map(InspectionLocal::mapToDomainModel)
                it.habit.mapToDomainModel().copy(performances = inspections)
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

    override fun getHabitValues(): LiveData<List<Habit>> =
        Transformations.map(habitDao.getHabitsValue()) {
            it.map { item ->
                item.mapToDomainModel()
            }
        }


}