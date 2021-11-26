package com.track.trackhabit.habit.presentation.ui.profile

import androidx.lifecycle.ViewModel
import com.track.common.base.AppDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dispatcher: AppDispatchers
): ViewModel() {
}