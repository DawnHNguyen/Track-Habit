package com.track.trackhabit.habit.presentation.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.track.common.base.AppDispatchers
import com.track.trackhabit.habit.domain.usecase.GetEmailTokenUseCase
import com.track.trackhabit.habit.domain.usecase.LoginUseCase
import com.track.trackhabit.habit.domain.usecase.RegisterUseCase
import com.track.trackhabit.habit.domain.usecase.VerifyEmailTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val fullname = MutableLiveData<String>()
    val verifyCode = MutableLiveData<String>()

    fun login() {
        viewModelScope.launch(dispatcher.main) {
            withContext(dispatcher.io) {
                loginUseCase(username.value.toString(), password.value.toString())
            }
        }
    }

}