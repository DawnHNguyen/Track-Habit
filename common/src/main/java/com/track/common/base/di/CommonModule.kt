package com.track.common.base.di

import com.track.common.base.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object CommonModule {
    @Provides
    fun provideAppDispatchers() =
        AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default)
}