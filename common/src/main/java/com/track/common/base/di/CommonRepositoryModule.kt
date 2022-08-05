package com.track.common.base.di

import com.track.common.base.data.repository.CommonRepositoryImpl
import com.track.common.base.domain.repository.CommonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CommonRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindTrackHabitRepository(impl: CommonRepositoryImpl): CommonRepository
}