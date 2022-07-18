package com.track.trackhabit.auth.presentation.ui.auth

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.track.common.base.AppDispatchers
import com.track.trackhabit.auth.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.auth.data.remote.auth.dto.response.RegisterResponse
import com.track.trackhabit.auth.data.remote.util.Resource
import com.track.trackhabit.auth.domain.usecase.GetEmailTokenUseCase
import com.track.trackhabit.auth.domain.usecase.LoginUseCase
import com.track.trackhabit.auth.domain.usecase.RegisterUseCase
import com.track.trackhabit.auth.domain.usecase.VerifyEmailTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dispatcher: AppDispatchers,
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val getEmailTokenUseCase: GetEmailTokenUseCase,
    private val verifyEmailTokenUseCase: VerifyEmailTokenUseCase
) : ViewModel() {

    private var _loginStateFlow: MutableStateFlow<Resource<LoginResponse>> = MutableStateFlow(Resource.loading())
    val loginStateFlow: StateFlow<Resource<LoginResponse>> get() = _loginStateFlow

    private var _registerStateFlow: MutableStateFlow<Resource<RegisterResponse>> = MutableStateFlow(Resource.loading())
    val registerStateFlow: StateFlow<Resource<RegisterResponse>> get() = _registerStateFlow

    val progressBarVisibility = MutableLiveData(View.GONE)

    private val _confirmPasswordErrorVisibility = MutableLiveData(View.GONE)
    val confirmPasswordErrorVisibility: LiveData<Int>
        get() = _confirmPasswordErrorVisibility

    private val _passwordErrorVisibility = MutableLiveData(View.GONE)
    val passwordErrorVisibility: LiveData<Int>
        get() = _passwordErrorVisibility

    private val _usernameErrorVisibility = MutableLiveData(View.GONE)
    val usernameErrorVisibility: LiveData<Int>
        get() = _usernameErrorVisibility

    private val _fullNameErrorVisibility = MutableLiveData(View.GONE)
    val fullNameErrorVisibility: LiveData<Int>
        get() = _fullNameErrorVisibility

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val fullName = MutableLiveData<String>()
    val verifyCode = MutableLiveData<String>()

    fun login() {
        _loginStateFlow.value = Resource.loading()
        updateLoginProgressBarVisibility()
        viewModelScope.launch(dispatcher.main) {
            withContext(dispatcher.io) {
                val loginResponse = loginUseCase(username.value.toString(), password.value.toString())
                _loginStateFlow.tryEmit(loginResponse)
            }
        }
    }

    private fun updateLoginProgressBarVisibility() {
        viewModelScope.launch(dispatcher.main) {
            loginStateFlow.collect {
                progressBarVisibility.value = if (it.isLoading()) View.VISIBLE else View.GONE
            }
        }
    }

    fun register() {
        if (
            (username.value?.length ?: 0) < 6 ||
            (password.value?.length ?: 0) < 8 ||
            password.value != confirmPassword.value ||
            (fullName.value?.length ?: 0) < 6
        ) {
            if ((username.value?.length ?: 0) < 6) _usernameErrorVisibility.value = View.VISIBLE
            if ((password.value?.length ?: 0) < 8) _passwordErrorVisibility.value = View.VISIBLE
            if (password.value != confirmPassword.value) _confirmPasswordErrorVisibility.value = View.VISIBLE
            if ((fullName.value?.length ?: 0) < 6) _fullNameErrorVisibility.value = View.VISIBLE
        } else {
            _registerStateFlow.value = Resource.loading()
            updateRegisterProgressBarVisibility()
            viewModelScope.launch(dispatcher.main) {
                withContext(dispatcher.io) {
                    val registerResponse =
                        registerUseCase(
                            email.value.toString(),
                            username.value.toString(),
                            password.value.toString(),
                            fullName.value.toString()
                        )
                    _registerStateFlow.tryEmit(registerResponse)
                }
            }
        }
    }

    private fun updateRegisterProgressBarVisibility() {
        viewModelScope.launch(dispatcher.main) {
            registerStateFlow.collect {
                progressBarVisibility.value = if (it.isLoading()) View.VISIBLE else View.GONE
            }
        }
    }

}