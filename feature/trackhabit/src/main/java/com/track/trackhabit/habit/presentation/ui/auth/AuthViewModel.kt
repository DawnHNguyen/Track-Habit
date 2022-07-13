package com.track.trackhabit.habit.presentation.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.track.common.base.AppDispatchers
import com.track.trackhabit.habit.data.remote.auth.dto.response.LoginResponse
import com.track.trackhabit.habit.data.remote.util.Resource
import com.track.trackhabit.habit.domain.usecase.GetEmailTokenUseCase
import com.track.trackhabit.habit.domain.usecase.LoginUseCase
import com.track.trackhabit.habit.domain.usecase.RegisterUseCase
import com.track.trackhabit.habit.domain.usecase.VerifyEmailTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val fullname = MutableLiveData<String>()
    val verifyCode = MutableLiveData<String>()

    fun login() {
        _loginStateFlow.value = Resource.loading()
        viewModelScope.launch(dispatcher.main) {
            withContext(dispatcher.io) {
                val loginResponse = loginUseCase(username.value.toString(), password.value.toString())
                _loginStateFlow.tryEmit(loginResponse)
            }
        }
    }

}