package com.track.trackhabit.habit.data.di

import com.track.trackhabit.habit.data.remote.services.HabitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackHabitRemoteModule {
    @Singleton
    @Provides
    fun providesTrackHabitService(retrofit: Retrofit): HabitService {
        return retrofit.create(HabitService::class.java)
    }
}