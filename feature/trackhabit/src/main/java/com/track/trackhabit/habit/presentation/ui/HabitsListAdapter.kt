package com.track.trackhabit.habit.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.track.common.base.utils.checkIsTodayDateFormatValue
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
                val context = root.context
                layoutItem.background = if (isNotiToday) {
                    val drawableRes = when {
                        isDoneToday -> R.drawable.background_itemhabit_done
                        checkIsBeforeHabit(habitTime) -> R.drawable.background_itemhabit_default
                        else -> R.drawable.background_itemhabit_missed
                    }
                    ContextCompat.getDrawable(context, drawableRes)
                } else ContextCompat.getDrawable(context, R.drawable.background_itemhabit_default)

                if ((checkIsBeforeHabit(habitTime) && !isDoneToday) || !isNotiToday) {
                    textviewItemhabitTitle.setTextColor(context.getColor(R.color.black))
                    textviewItemhabitTime.setTextColor(context.getColor(R.color.black))
                } else {
                    textviewItemhabitTitle.setTextColor(context.getColor(R.color.white))
                    textviewItemhabitTime.setTextColor(context.getColor(R.color.white))
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
                performance.last().check && checkIsTodayDateFormatValue(performance.last().time)
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