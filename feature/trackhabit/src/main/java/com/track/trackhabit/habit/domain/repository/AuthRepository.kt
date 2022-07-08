package com.track.trackhabit.habit.domain.repository

import com.track.trackhabit.habit.data.remote.auth.dto.response.EmailTokenResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.RegisterResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.VerifyEmailTokenResponse
import com.track.trackhabit.habit.data.remote.util.Resource


interface AuthRepository {
    suspend fun register(
        email: String,
        username: String,
        password: String,
        fullname: String
    ): Resource<RegisterResponse>

    suspend fun getEmailToken(email: String): Resource<EmailTokenResponse>

    suspend fun verifyEmailToken(email: String, code: String): Resource<VerifyEmailTokenResponse>

    suspend fun login(username: String, password: String): Resource<LoginResponse>
}