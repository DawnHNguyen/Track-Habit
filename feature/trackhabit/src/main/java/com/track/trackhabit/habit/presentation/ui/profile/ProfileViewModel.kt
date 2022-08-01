package com.track.trackhabit.habit.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.track.common.base.AppDispatchers
import com.track.trackhabit.habit.domain.usecase.AddLanguagePrefUseCase
import com.track.trackhabit.habit.domain.usecase.GetValueIsUseAccUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dispatcher: AppDispatchers,
    private val addLanguagePrefUseCase: AddLanguagePrefUseCase,
    private val getValueIsUseAccUseCase: GetValueIsUseAccUseCase
): ViewModel() {

    private val _isUseAcc = MutableLiveData(false)
    val isUseAcc: LiveData<Boolean> get() = _isUseAcc

    init {
        getValueIsUseAcc()
    }
    fun addLanguagePref(languageCode: String){
        viewModelScope.launch {
            withContext(dispatcher.io){
                addLanguagePrefUseCase(languageCode)
            }
        }
    }

    private fun getValueIsUseAcc(){
        _isUseAcc.value = getValueIsUseAccUseCase()
    }
}