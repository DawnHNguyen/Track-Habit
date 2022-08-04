package com.track.trackhabit.habit.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.track.trackhabit.habit.data.local.dao.HabitDao
import com.track.trackhabit.habit.data.local.dao.InspectionDao
import com.track.trackhabit.habit.data.local.dao.UserDao
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import com.track.trackhabit.habit.domain.entity.local.InspectionLocal
import com.track.trackhabit.habit.domain.entity.local.UserLocal
import kotlinx.coroutines.runBlocking
import java.util.*

@Database(
    entities = [UserLocal::class, HabitLocal::class, InspectionLocal::class],
     exportSchema = true,
    version = 6,
    autoMigrations = [
        AutoMigration(from = 4, to = 5),
        AutoMigration(from = 5, to = 6)
    ]
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun habitDao(): HabitDao
    abstract fun inspectionDao(): InspectionDao

    companion object{

        @Synchronized
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UserDatabase::class.java,
                "track_habit_db"
            ).fallbackToDestructiveMigration()
                .addCallback(userCallback)
                .build()

        private val userCallback = object : Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("INSERT INTO UserLocal VALUES ('AAAA','Squad 1','0','0')")
            }
        }

    }


}