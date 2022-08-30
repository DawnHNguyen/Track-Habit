package com.track.trackhabit.habit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.track.common.base.data.remote.util.NetworkBoundResource
import com.track.common.base.data.remote.util.Resource
import com.track.common.base.utils.DISPLAY_DATE_FORMAT
import com.track.common.base.utils.toDate
import com.track.trackhabit.habit.data.local.dao.HabitDao
import com.track.trackhabit.habit.data.remote.services.HabitDataSource
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import com.track.trackhabit.habit.domain.entity.local.InspectionOwner
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
                val habitOwner = habitDao.getHabit()
                val listHabitLocal = ArrayList<Habit>()
                habitOwner.value?.forEach{
                    val localHabit = it.habit
                    listHabitLocal.add(localHabit.mapToDomainModel())
                }
                return listHabitLocal
            }

            override suspend fun createCallAsync(): Response<List<HabitDto>> {
                return habitDataSource.getHabit()
            }

            override fun processResponse(response: Response<List<HabitDto>>): List<Habit> {
                return response.body()!!.forEach {
                    it.mapToDomainModel()
                } as List<Habit>
            }

            override suspend fun saveCallResults(items: List<Habit>) {
                items.forEach {
                    habitDao.insertHabit(it.toLocalDto())
                }

            }

            override suspend fun shouldFetch(data: List<Habit>?): Boolean {
                if (data != null) {
                    val listDto = habitDataSource.getHabit()
                    if (!listDto.body().isNullOrEmpty()){
                        if (listDto.body()?.size != data.size){
                            return true
                        } else{
                            for (i in data.indices){
                                val habitDto = listDto.body()!![i]
                                if (data[i].updateAt != (habitDto.updateAt.toDate(
                                        DISPLAY_DATE_FORMAT
                                    ) ?: Date())
                                )return true
                            }
                        }
                        return false
                    }
                }
                return true
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
}