package com.track.trackhabit.auth.data.di

import com.track.trackhabit.auth.data.remote.auth.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationRemoteModule {
    @Singleton
    @Provides
    fun providesAuthenticationService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}