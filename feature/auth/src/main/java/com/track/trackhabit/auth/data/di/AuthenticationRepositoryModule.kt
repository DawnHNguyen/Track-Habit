package com.track.trackhabit.auth.data.di

import com.track.trackhabit.auth.data.repository.AuthRepositoryImpl
import com.track.trackhabit.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AuthenticationRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}