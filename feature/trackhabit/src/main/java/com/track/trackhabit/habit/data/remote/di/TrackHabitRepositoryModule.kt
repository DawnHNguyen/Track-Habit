package com.track.trackhabit.habit.data.remote.di

import com.track.trackhabit.habit.data.repository.InspectionRepositoryImpl
import com.track.trackhabit.habit.data.repository.LanguageRepositoryImpl
import com.track.trackhabit.habit.data.repository.TrackHabitRepositoryImpl
import com.track.trackhabit.habit.data.repository.UserRepositoryImpl
import com.track.trackhabit.habit.domain.repository.InspectionRepository
import com.track.trackhabit.habit.domain.repository.LanguageRepository
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

    @Singleton
    @Binds
    abstract fun bindInspectionRepository(impl: InspectionRepositoryImpl): InspectionRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindLanguageRepository(impl: LanguageRepositoryImpl): LanguageRepository
}