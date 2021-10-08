package com.track.trackhabit.habit.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Dao
abstract class HabitDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHabit(habit: HabitLocal)

    @Query("SELECT * FROM habitlocal")
    abstract fun getHabit(): LiveData<List<HabitLocal>>

    @Update
    abstract suspend fun updateHabit(habit: HabitLocal)

    @Query("DELETE FROM habitlocal WHERE habit_id=:id")
    abstract suspend fun deleteHabit(id: Int)
}