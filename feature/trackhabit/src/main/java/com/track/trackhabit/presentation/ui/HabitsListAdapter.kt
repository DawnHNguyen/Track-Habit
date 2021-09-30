package com.track.trackhabit.presentation.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.track.trackhabit.domain.entity.Habit

class HabitsListAdapter() :
    ListAdapter<Habit, HabitsListAdapter.HabitsListViewHolder>(HabitsListDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HabitsListViewHolder = HabitsListViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: HabitsListViewHolder,
        position: Int,
    ) {
        val habit = getItem(position)
        holder.bind(habit)
    }

    class HabitsListViewHolder private constructor(private val binding: )

    class HabitsListDiffUtil() : DiffUtil.ItemCallback<Habit>() {
        override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit) = oldItem.id == newItem.id
    }
}