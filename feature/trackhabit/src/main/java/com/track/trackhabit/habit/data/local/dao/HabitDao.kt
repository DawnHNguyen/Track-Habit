package com.track.trackhabit.habit.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import com.track.trackhabit.habit.domain.entity.local.HabitOwner
import com.track.trackhabit.habit.domain.entity.local.InspectionOwner

@Dao
abstract class HabitDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHabit(habit: HabitLocal): Long

    @Transaction
    @Query("SELECT * FROM habitLocal")
    abstract fun getHabit(): LiveData<List<InspectionOwner>>

    @Query("SELECT * FROM habitLocal")
    abstract suspend fun getHabitList(): List<InspectionOwner>

    @Query("SELECT * FROM UserLocal WHERE user_id = :idUser")
    abstract fun getHabitByUser(idUser: String): LiveData<HabitOwner>

    @Query("SELECT * FROM habitLocal WHERE habit_id=:id")
    abstract fun getHabitById(id: Int): LiveData<HabitLocal>

    @Query("SELECT * FROM habitLocal WHERE habit_id=:id")
    abstract suspend fun getHabitValueById(id: Int): HabitLocal

    @Update
    abstract suspend fun updateHabit(habit: HabitLocal)

    @Query("DELETE FROM habitLocal WHERE habit_id=:id")
    abstract suspend fun deleteHabit(id: Int)

    @Query("SELECT * FROM habitLocal")
    abstract fun getHabitsValue(): LiveData<List<HabitLocal>>
}