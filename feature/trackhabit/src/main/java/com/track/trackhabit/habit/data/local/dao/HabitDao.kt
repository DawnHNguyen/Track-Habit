package com.track.trackhabit.habit.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import com.track.trackhabit.habit.domain.entity.local.HabitOwner

@Dao
abstract class HabitDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHabit(habit: HabitLocal): Long

    @Query("SELECT * FROM habitlocal")
    abstract fun getHabit(): LiveData<List<HabitLocal>>

    @Query("SELECT * FROM UserLocal WHERE user_id = :id")
    abstract fun getHabitByUser(id: String): LiveData<HabitOwner>

    @Query("SELECT * FROM habitLocal WHERE habit_id=:id")
    abstract fun getHabitById(id: Int): LiveData<HabitLocal>

    @Query("SELECT * FROM habitLocal WHERE habit_id=:id")
    abstract suspend fun getHabitValueById(id: Int): HabitLocal

    @Update
    abstract suspend fun updateHabit(habit: HabitLocal)

    @Query("DELETE FROM habitlocal WHERE habit_id=:id")
    abstract suspend fun deleteHabit(id: Int)
}