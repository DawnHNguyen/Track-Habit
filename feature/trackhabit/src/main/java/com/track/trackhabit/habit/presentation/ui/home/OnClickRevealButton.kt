package com.track.trackhabit.habit.presentation.ui.home

import com.track.trackhabit.habit.domain.entity.Habit

interface OnClickRevealButton {
    fun onClickEdit(habit: Habit)
    fun onClickDelete(habit: Habit)
    fun onClickUpdate(habit: Habit)
}