package com.track.trackhabit.data.di

import android.content.Context
import com.track.trackhabit.habit.data.local.UserDatabase
import com.track.trackhabit.habit.data.local.dao.HabitDao
import com.track.trackhabit.habit.data.local.dao.InspectionDao
import com.track.trackhabit.habit.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TrackHabitLocalModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase =
        UserDatabase.buildDatabase(context)

    @Singleton
    @Provides
    fun provideUserDao(database: UserDatabase): UserDao = database.userDao()

    @Singleton
    @Provides
    fun provideHabitDao(database: UserDatabase): HabitDao = database.habitDao()

    @Singleton
    @Provides
    fun provideInspectionDao(database: UserDatabase): InspectionDao = database.inspectionDao()
}
