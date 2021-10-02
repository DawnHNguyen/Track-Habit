package com.track.trackhabit.data.local.dao

import androidx.room.*
import com.track.trackhabit.domain.entity.local.UserLocal

@Dao
abstract class UserDao() {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUser(user: UserLocal)
}