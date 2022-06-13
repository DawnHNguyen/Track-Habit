package com.track.trackhabit.habit.presentation.ui.sleeptime

import android.view.View

data class SleeptimeUIState(
    var isChosenWaketime: Boolean = false,
    var isEnabledConfirmSleeptimeButton: Boolean = false

) {
    val confirmWakeTimeVisibility get() = if (isChosenWaketime) View.GONE else View.VISIBLE
    val clearRemindButtonVisibility get() = if (isChosenWaketime) View.GONE else View.VISIBLE
    val sleepTimeTitleVisibility get() = if (isChosenWaketime) View.VISIBLE else View.INVISIBLE
    val confirmSleeptimeVisibility get() = if (isChosenWaketime) View.VISIBLE else View.INVISIBLE
    val backButtonVisibility get() = if (isChosenWaketime) View.VISIBLE else View.INVISIBLE
    val timePickerClickable get() = !isChosenWaketime
}
