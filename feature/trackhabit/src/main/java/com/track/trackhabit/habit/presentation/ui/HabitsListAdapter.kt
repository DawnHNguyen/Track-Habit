package com.track.trackhabit.habit.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.ItemHabitBinding
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.presentation.ui.home.OnClickRevealButton
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


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
        viewBinderHelper.bind(holder.swipeRevealLayout, habit.habitId.toString())
        viewBinderHelper.setOpenOnlyOne(true)
        holder.bind(habit, onclick)
    }

    class HabitsListViewHolder private constructor(private val binding: ItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val swipeRevealLayout = binding.layoutOnSwipeReveal
        fun bind(habit: Habit, onclick: OnClickRevealButton) {
            binding.habit = habit
            binding.onClick = onclick
            binding.textviewItemhabitTime.text = SimpleDateFormat("HH:mm").format(habit.time)
            changeHabitColor(habit.time, habit.isNotiToday(), habit.performances)
            binding.executePendingBindings()
        }

        private fun changeHabitColor(
            habitTime: Date,
            isNotiToday: Boolean,
            performance: List<Inspection>
        ) {
            with(binding) {
                val isDoneToday = checkIsDoneToday(performance)
                val resources = root.context.resources
                layoutItem.background = if (isNotiToday) {
                    if (checkIsBeforeHabit(habitTime) || isDoneToday) {
                        if (isDoneToday) {
                            resources.getDrawable(R.drawable.background_itemhabit_done)
                        } else {
                            resources.getDrawable(R.drawable.background_itemhabit_default)
                        }
                    } else resources.getDrawable(R.drawable.background_itemhabit_missed)
                } else resources.getDrawable(R.drawable.background_itemhabit_default)

                if ((checkIsBeforeHabit(habitTime) && !isDoneToday) || !isNotiToday) {
                    layoutItem.isEnabled = isNotiToday
                    textviewItemhabitTitle.setTextColor(resources.getColor(R.color.black))
                    textviewItemhabitTime.setTextColor(resources.getColor(R.color.black))
                } else {
                    layoutItem.isEnabled = false
                    textviewItemhabitTitle.setTextColor(resources.getColor(R.color.white))
                    textviewItemhabitTime.setTextColor(resources.getColor(R.color.white))
                }
            }
        }

        private fun checkIsBeforeHabit(habitTime: Date): Boolean {
            val cal = Calendar.getInstance()
            cal.timeInMillis -= TimeUnit.MINUTES.toMillis(10)
            val presentTimeArr = SimpleDateFormat("HH:mm").format(cal.time).split(":")
            val habitTimeArr = SimpleDateFormat("HH:mm").format(habitTime).split(":")
            return presentTimeArr[0].toInt() < habitTimeArr[0].toInt() || (presentTimeArr[0].toInt() == habitTimeArr[0].toInt() && presentTimeArr[1].toInt() < habitTimeArr[1].toInt())
        }

        private fun checkIsDoneToday(performance: List<Inspection>): Boolean {
            return if (performance.isEmpty()) false
            else {
                val cal = Calendar.getInstance().apply {
                    set(Calendar.HOUR, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val calLast = Calendar.getInstance().apply {
                    set(Calendar.HOUR, 24)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                performance.last().check && (performance.last().time in cal.time..calLast.time)
            }
        }

        companion object {
            fun from(parent: ViewGroup): HabitsListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHabitBinding.inflate(layoutInflater, parent, false)
                return HabitsListViewHolder(binding)
            }
        }
    }

    class HabitsListDiffUtil : DiffUtil.ItemCallback<Habit>() {
        override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit) =
            oldItem.habitId == newItem.habitId
    }

    fun saveStates(outState: Bundle?) {
        viewBinderHelper.saveStates(outState)
    }

    fun restoreStates(inState: Bundle?) {
        viewBinderHelper.restoreStates(inState)
    }

}