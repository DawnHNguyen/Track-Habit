package com.track.trackhabit.habit.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal

@Dao
abstract class InspectionDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertInspection(inspectionLocal: InspectionLocal)

    @Query("SELECT * FROM inspectionlocal")
    abstract fun getInspection(): LiveData<List<InspectionLocal>>
}