package com.track.trackhabit.habit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.track.trackhabit.habit.domain.entity.local.UserLocal

@Dao
abstract class UserDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUser(user: UserLocal)
}