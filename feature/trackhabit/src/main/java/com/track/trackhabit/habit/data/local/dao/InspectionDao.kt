package com.track.trackhabit.habit.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import com.track.trackhabit.habit.domain.entity.local.InspectionOwner

@Dao
abstract class InspectionDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertInspection(inspectionLocal: InspectionLocal)

    @Update
    abstract suspend fun updateInspection(inspectionLocal: InspectionLocal)

    @Query("SELECT * FROM inspectionlocal")
    abstract fun getInspection(): LiveData<List<InspectionLocal>>

    @Query("SELECT * FROM HabitLocal WHERE habit_id = :id")
    abstract fun getInspectionByHabit(id: Int): LiveData<InspectionOwner>
}