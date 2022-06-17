package com.track.trackhabit.habit.presentation.ui

import androidx.lifecycle.ViewModel
import com.track.common.base.AppDispatchers
import com.track.trackhabit.habit.domain.usecase.GetLanguagePrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    private val dispatcher: AppDispatchers,
    private val getLanguagePrefUseCase: GetLanguagePrefUseCase
) : ViewModel() {

    fun getLanguagePref(): String = getLanguagePrefUseCase()
}