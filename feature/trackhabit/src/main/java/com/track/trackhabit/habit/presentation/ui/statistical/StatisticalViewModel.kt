package com.track.trackhabit.habit.presentation.ui.statistical

import androidx.lifecycle.ViewModel
import com.track.common.base.AppDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticalViewModel @Inject constructor(
    private val dispatchers: AppDispatchers
) : ViewModel() {
}