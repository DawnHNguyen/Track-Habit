package com.track.trackhabit.data.di.dao

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.track.trackhabit.domain.entity.User

@Database(entities = [User::class],version = 1,exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

}