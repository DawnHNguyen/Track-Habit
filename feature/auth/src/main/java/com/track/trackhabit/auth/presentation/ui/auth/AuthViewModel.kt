package com.track.trackhabit.auth.presentation.ui.auth

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.track.common.base.AppDispatchers
import com.track.trackhabit.auth.data.remote.auth.dto.response.EmailTokenResponse
import com.track.trackhabit.auth.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.auth.data.remote.auth.dto.response.RegisterResponse
import com.track.trackhabit.auth.data.remote.auth.dto.response.VerifyEmailTokenResponse
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

    private var _getCodeStateFlow: MutableStateFlow<Resource<EmailTokenResponse>> = MutableStateFlow(Resource.loading())
    val getCodeStateFlow: StateFlow<Resource<EmailTokenResponse>> get() = _getCodeStateFlow

    private var _verifyEmailStateFlow: MutableStateFlow<Resource<VerifyEmailTokenResponse>> = MutableStateFlow(Resource.loading())
    val verifyEmailStateFlow: StateFlow<Resource<VerifyEmailTokenResponse>> get() = _verifyEmailStateFlow

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
        if(!isValidInput()) return
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

    private fun isValidInput(): Boolean{
        var isValid = true

        if(!isValidUsernameInput()) isValid = false
        if(!isValidPasswordInput()) isValid = false
        if(!isMatchConfirmPassword()) isValid = false
        if(!isValidFullNameInput()) isValid = false

        return isValid
    }

    private fun isValidUsernameInput(): Boolean{
        return if ((username.value?.length ?: 0) < 6) {
            _usernameErrorVisibility.value = View.VISIBLE
            false
        } else {
            _usernameErrorVisibility.value = View.GONE
            true
        }
    }

    private fun isValidPasswordInput(): Boolean{
        return if ((password.value?.length ?: 0) < 8) {
            _passwordErrorVisibility.value = View.VISIBLE
            false
        } else {
            _passwordErrorVisibility.value = View.GONE
            true
        }
    }

    private fun isMatchConfirmPassword(): Boolean{
        return if (password.value != confirmPassword.value) {
            _confirmPasswordErrorVisibility.value = View.VISIBLE
            false
        } else {
            _confirmPasswordErrorVisibility.value = View.GONE
            true
        }
    }

    private fun isValidFullNameInput(): Boolean{
        return if ((fullName.value?.length ?: 0) < 6) {
            _fullNameErrorVisibility.value = View.VISIBLE
            false
        } else {
            _fullNameErrorVisibility.value = View.GONE
            true
        }
    }

    private fun updateRegisterProgressBarVisibility() {
        viewModelScope.launch(dispatcher.main) {
            registerStateFlow.collect {
                progressBarVisibility.value = if (it.isLoading()) View.VISIBLE else View.GONE
            }
        }
    }

    fun verifyEmail(){
        _verifyEmailStateFlow.value = Resource.loading()
        updateVerifyEmailProgressBarVisibility()
        viewModelScope.launch(dispatcher.main) {
            withContext(dispatcher.io) {
                val verifyEmailResponse = verifyEmailTokenUseCase(email.value.toString(), verifyCode.value.toString())
                _verifyEmailStateFlow.tryEmit(verifyEmailResponse)
            }
        }
    }

    private fun updateVerifyEmailProgressBarVisibility() {
        viewModelScope.launch(dispatcher.main) {
            verifyEmailStateFlow.collect {
                progressBarVisibility.value = if (it.isLoading()) View.VISIBLE else View.GONE
            }
        }
    }

    fun getEmailToken(){
        viewModelScope.launch(dispatcher.main) {
            withContext(dispatcher.io) {
                val getEmailToken = getEmailTokenUseCase(email.value.toString())
                _getCodeStateFlow.tryEmit(getEmailToken)
            }
        }
    }

}