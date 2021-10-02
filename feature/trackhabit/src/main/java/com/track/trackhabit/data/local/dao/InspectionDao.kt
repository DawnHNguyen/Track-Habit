package com.track.trackhabit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.track.trackhabit.domain.entity.local.InspectionLocal

@Dao
abstract class InspectionDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertInspection(inspectionLocal: InspectionLocal)

    @Query("SELECT * FROM inspection")
    abstract suspend fun getInspection(): List<InspectionLocal>
}