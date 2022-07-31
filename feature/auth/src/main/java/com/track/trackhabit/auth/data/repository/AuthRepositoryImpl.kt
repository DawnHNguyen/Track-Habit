package com.track.trackhabit.auth.data.repository

import com.orhanobut.hawk.Hawk
import com.track.common.base.constpackage.HawkKey
import com.track.common.base.data.remote.util.Resource
import com.track.trackhabit.auth.data.remote.auth.dto.EmailTokenRequest
import com.track.trackhabit.auth.data.remote.auth.dto.LoginRequest
import com.track.trackhabit.auth.data.remote.auth.dto.RegisterRequest
import com.track.trackhabit.auth.data.remote.auth.dto.VerifyEmailTokenRequest
import com.track.trackhabit.auth.data.remote.auth.dto.response.EmailTokenResponse
import com.track.trackhabit.auth.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.auth.data.remote.auth.dto.response.RegisterResponse
import com.track.trackhabit.auth.data.remote.auth.dto.response.VerifyEmailTokenResponse
import com.track.trackhabit.auth.data.remote.auth.services.AuthDataSource
import com.track.trackhabit.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) :
    AuthRepository {
    override suspend fun register(
        email: String,
        username: String,
        password: String,
        fullName: String
    ): Resource<RegisterResponse> {
        val registerRequest = RegisterRequest(email, username, password, fullName)
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
        val loginResponse = authDataSource.login(loginRequest)
        if (loginResponse.isSuccessful()) {
            Hawk.put(HawkKey.ACCESS_TOKEN, loginResponse.data?.accessToken)
            Hawk.put(HawkKey.REFRESH_TOKEN, loginResponse.data?.refreshToken)
            Hawk.put(HawkKey.IS_USE_ACC, true)
        }
        return loginResponse
    }

    override suspend fun skipAccount() {
        Hawk.put(HawkKey.IS_USE_ACC, false)
    }
}