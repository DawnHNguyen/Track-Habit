package com.track.trackhabit.data.di

import com.track.trackhabit.data.repository.TrackHabitRepositoryImpl
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class TrackHabitRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindTrackHabitRepository(impl: TrackHabitRepositoryImpl): TrackHabitRepository
}