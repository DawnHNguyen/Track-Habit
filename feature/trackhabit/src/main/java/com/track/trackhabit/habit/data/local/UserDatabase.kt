package com.track.trackhabit.habit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.track.trackhabit.habit.data.local.dao.HabitDao
import com.track.trackhabit.habit.data.local.dao.InspectionDao
import com.track.trackhabit.habit.data.local.dao.UserDao
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import com.track.trackhabit.habit.domain.entity.local.UserLocal

@Database(
    entities = [UserLocal::class, HabitLocal::class, InspectionLocal::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun habitDao(): HabitDao
    abstract fun inspectionDao(): InspectionDao

    companion object{
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "track_habit_db")
                .build()
    }
}