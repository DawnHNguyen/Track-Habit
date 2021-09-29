package com.track.trackhabit.data.local

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.track.trackhabit.data.local.dao.HabitDao
import com.track.trackhabit.data.local.dao.InspectionDao
import com.track.trackhabit.data.local.dao.UserDao
import com.track.trackhabit.domain.entity.User

@Database(entities = [User::class],version = 1,exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun habitDao(): HabitDao
    abstract fun inspectionDao(): InspectionDao
}