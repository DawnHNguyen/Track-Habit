package com.track.trackhabit.habit.data.repository

import com.track.trackhabit.habit.data.remote.auth.dto.EmailTokenRequest
import com.track.trackhabit.habit.data.remote.auth.dto.LoginRequest
import com.track.trackhabit.habit.data.remote.auth.dto.RegisterRequest
import com.track.trackhabit.habit.data.remote.auth.dto.VerifyEmailTokenRequest
import com.track.trackhabit.habit.data.remote.auth.dto.response.EmailTokenResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.RegisterResponse
import com.track.trackhabit.habit.data.remote.auth.dto.response.VerifyEmailTokenResponse
import com.track.trackhabit.habit.data.remote.auth.services.AuthDataSource
import com.track.trackhabit.habit.data.remote.util.Resource
import com.track.trackhabit.habit.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) :
    AuthRepository {
    override suspend fun register(
        email: String,
        username: String,
        password: String,
        fullname: String
    ): Resource<RegisterResponse> {
        val registerRequest = RegisterRequest(email, username, password, fullname)
        return authDataSource.register(registerRequest)
    }

    override suspend fun getEmailToken(email: String): Resource<EmailTokenResponse> {
        val emailTokenRequest = EmailTokenRequest(email)
        return authDataSource.getEmailToken(emailTokenRequest)
    }

    override suspend fun verifyEmailToken(
        email: String,
        code: String
    ): Resource<VerifyEmailTokenResponse> {
        val verifyEmailTokenRequest = VerifyEmailTokenRequest(email, code)
        return authDataSource.verifyEmailToken(verifyEmailTokenRequest)
    }

    override suspend fun login(username: String, password: String): Resource<LoginResponse> {
        val loginRequest = LoginRequest(username, password)
        return authDataSource.login(loginRequest)
    }
}