package com.track.trackhabit.habit.data.di

import com.track.trackhabit.habit.data.repository.InspectionRepositoryImpl
import com.track.trackhabit.habit.data.repository.TrackHabitRepositoryImpl
import com.track.trackhabit.habit.data.repository.UserRepositoryImpl
import com.track.trackhabit.habit.domain.repository.InspectionRepository
import com.track.trackhabit.habit.domain.repository.TrackHabitRepository
import com.track.trackhabit.habit.domain.repository.UserRepository
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
    @Binds
    abstract fun bindInspectionRepository(impl: InspectionRepositoryImpl): InspectionRepository
    @Binds
    abstract fun bindUserREpository(impl: UserRepositoryImpl): UserRepository
}