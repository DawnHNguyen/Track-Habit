package com.track.trackhabit.data.local.dao

import androidx.room.*
import com.track.trackhabit.domain.entity.local.HabitLocal

@Dao
abstract class HabitDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHabit(habit: HabitLocal)

    @Query("SELECT * FROM habit")
    abstract suspend fun getHabit(): List<HabitLocal>

    @Update
    abstract suspend fun updateHabit(habit: HabitLocal)

    @Query("DELETE FROM habit WHERE id=:id")
    abstract suspend fun deleteHabit(id: Int)
}