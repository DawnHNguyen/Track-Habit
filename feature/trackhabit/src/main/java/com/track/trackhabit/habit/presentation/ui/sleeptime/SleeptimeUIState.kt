package com.track.trackhabit.habit.presentation.ui.sleeptime

data class SleeptimeUIState(
    var conFirmWakeTimeVisibility: Int,
    var sleepTimeTitleVisibility: Int,
    var confirmSleeptimeVisibility: Int,
    var backButtonVisibility: Int,
    var isEnabledConfirmSleeptimeButton: Boolean,
    var timePickerClickable: Boolean
)
