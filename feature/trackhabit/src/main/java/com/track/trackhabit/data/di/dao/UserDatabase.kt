package com.track.trackhabit.data.di.dao

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.track.trackhabit.domain.entity.Inspection
import com.track.trackhabit.domain.entity.User
import com.track.trackhabit.domain.entity.local.HabitLocal
import com.track.trackhabit.domain.entity.local.InspectionLocal
import com.track.trackhabit.domain.entity.local.UserLocal

@Database(entities = [UserLocal::class, HabitLocal::class, InspectionLocal::class],version = 1,exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

}