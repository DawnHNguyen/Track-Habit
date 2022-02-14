package com.track.trackhabit.habit.data.di

import android.content.Context
import com.track.trackhabit.habit.device.TrackHabitNotificationManger
import com.track.trackhabit.habit.presentation.ui.AlarmService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TrackHabitDeviceModule {
    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context) = TrackHabitNotificationManger(context)
    @Provides
    fun provideAlarmService(@ApplicationContext context: Context) = AlarmService(context)
}