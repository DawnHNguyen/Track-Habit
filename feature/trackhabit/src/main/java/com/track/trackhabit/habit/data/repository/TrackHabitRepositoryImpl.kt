package com.track.trackhabit.habit.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.track.common.base.data.remote.util.NetworkBoundResource
import com.track.common.base.data.remote.util.Resource
import com.track.trackhabit.habit.data.local.dao.HabitDao
import com.track.trackhabit.habit.data.remote.services.HabitDataSource
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import com.track.trackhabit.habit.domain.entity.remote.HabitDto
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class TrackHabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private val habitDataSource: HabitDataSource
): TrackHabitRepository {

    override suspend fun addHabit(habit: Habit): Long {
//        Đây là logic để test API, không uncomment ở đây vì logic navigate sẽ làm quá trình call API bị Cancel
//        val reminderDays = mutableListOf<Int>()
//        habit.frequency?.forEachIndexed { index, c ->
//            if(c == '1') reminderDays.add(index)
//        }
//        val reminderTime = SimpleDateFormat("HH:mm").format(habit.time.time)
//
//        val habitRequest = HabitRequest(null, reminderDays, reminderTime, habit.title)
////        habitDataSource.addHabit(addHabitRequest)
////        habitDataSource.updateHabit(43, habitRequest)
////        habitDataSource.getHabit("2022-07-29")
////        habitDataSource.deleteHabit(43)
        return habitDao.insertHabit(habit.toLocalDto())
    }

    override suspend fun getHabit(): LiveData<Resource<List<Habit>>> {
        return object : NetworkBoundResource<List<Habit>, List<HabitDto>>(){
            override suspend fun loadFromDb(): List<Habit>? {
                val listHabitLocal = ArrayList<Habit>()

                habitDao.getHabitList().map{ it ->
                    val inspections = it.listInspection.map(InspectionLocal::mapToDomainModel)
                    listHabitLocal.add(it.habit.mapToDomainModel().copy(performances = inspections))
                }
                return listHabitLocal
            }

            override suspend fun createCallAsync(): Response<List<HabitDto>> {
                return habitDataSource.getHabit()
            }

            override fun processResponse(response: Response<List<HabitDto>>): List<Habit> {
                val listHabitLocal = ArrayList<Habit>()
                if (response.body().isNullOrEmpty()) return emptyList()

                response.body()!!.forEach {
                    listHabitLocal.add(it.mapToDomainModel())
                }

                Log.d("checkListProcess","--$listHabitLocal")
                return  listHabitLocal
            }

            override suspend fun saveCallResults(items: List<Habit>) {
                habitDao.insertAllHabit(items.map {
                    it.toLocalDto().copy(updateAt = Date().time)
                })

            }

            override suspend fun shouldFetch(data: List<Habit>?): Boolean {
                if (data == null ) return true
                for (i in data.indices){
                    if (data[i].updateAt.time + SHOULD_UPDATE_DURATION_MILLIS < Date().time) return true
                }
                return false
            }

        }.build().asLiveData()
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

    companion object {
        const val SHOULD_UPDATE_DURATION_MILLIS = 15 * 60 * 1000
    }
}