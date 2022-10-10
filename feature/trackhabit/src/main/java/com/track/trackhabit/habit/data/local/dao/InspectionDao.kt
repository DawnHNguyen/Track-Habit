package com.track.trackhabit.habit.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import com.track.trackhabit.habit.domain.entity.local.InspectionOwner

@Dao
abstract class InspectionDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertInspection(inspectionLocal: InspectionLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertListInspection(listInspectionLocal: List<InspectionLocal>)
    @Update
    abstract suspend fun updateInspection(inspectionLocal: InspectionLocal)

    @Query("SELECT * FROM inspectionlocal")
    abstract fun getInspection(): LiveData<List<InspectionLocal>>

    @Query("SELECT * FROM inspectionlocal")
    abstract suspend fun getListInspection(): List<InspectionLocal>

    @Query("SELECT * FROM HabitLocal WHERE habit_id = :idHabit")
    abstract fun getInspectionByHabit(idHabit: Int): LiveData<InspectionOwner>
}