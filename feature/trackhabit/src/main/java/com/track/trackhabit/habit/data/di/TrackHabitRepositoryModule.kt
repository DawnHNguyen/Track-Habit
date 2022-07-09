package com.track.trackhabit.habit.data.di

import com.track.trackhabit.habit.data.repository.*
import com.track.trackhabit.habit.domain.repository.*
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

    @Singleton
    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}