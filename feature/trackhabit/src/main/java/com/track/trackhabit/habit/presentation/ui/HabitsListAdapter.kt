package com.track.trackhabit.habit.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.track.trackhabit.habit.databinding.ItemHabitBinding
import com.track.trackhabit.habit.domain.entity.Habit
import android.os.Bundle
import com.track.trackhabit.habit.presentation.ui.home.OnClickRevealButton
import timber.log.Timber


class HabitsListAdapter(private val onClickRevealButton: OnClickRevealButton) :
    ListAdapter<Habit, HabitsListAdapter.HabitsListViewHolder>(HabitsListDiffUtil()) {
    private var viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HabitsListViewHolder = HabitsListViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: HabitsListViewHolder,
        position: Int,
    ) {
        val habit = getItem(position)
        val onclick = onClickRevealButton
        viewBinderHelper.bind(holder.swipeRevealLayout,habit.habitId.toString())
        viewBinderHelper.setOpenOnlyOne(true)
        holder.bind(habit, onclick)
    }

    class HabitsListViewHolder private constructor(private val binding: ItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val swipeRevealLayout = binding.layoutOnSwipeReveal
        fun bind(habit: Habit, onclick: OnClickRevealButton) {
            binding.habit = habit
            binding.onClick = onclick
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HabitsListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHabitBinding.inflate(layoutInflater, parent, false)
                return HabitsListViewHolder(binding)
            }
        }
    }

    class HabitsListDiffUtil() : DiffUtil.ItemCallback<Habit>() {
        override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit) = oldItem.habitId == newItem.habitId
    }

    fun saveStates(outState: Bundle?) {
        viewBinderHelper.saveStates(outState)
    }

    fun restoreStates(inState: Bundle?) {
        viewBinderHelper.restoreStates(inState)
    }
}