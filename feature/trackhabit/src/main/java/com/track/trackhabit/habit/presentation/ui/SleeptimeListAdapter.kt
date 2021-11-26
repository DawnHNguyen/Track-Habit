package com.track.trackhabit.habit.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.track.trackhabit.habit.databinding.ItemSleepTimeBinding
import com.track.trackhabit.habit.domain.entity.Sleeptime

class SleeptimeListAdapter(): ListAdapter<Sleeptime, SleeptimeListAdapter.SleeptimeListViewHolder>(SleeptimeListDiffUtil()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SleeptimeListViewHolder = SleeptimeListViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: SleeptimeListViewHolder,
        position: Int,
    ) {
        val sleeptime = getItem(position)
        holder.bind(sleeptime)
    }

    class SleeptimeListViewHolder private constructor(private val binding: ItemSleepTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sleepTime: Sleeptime) {
            binding.sleeptime = sleepTime
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SleeptimeListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSleepTimeBinding.inflate(layoutInflater, parent, false)
                return SleeptimeListViewHolder(binding)
            }
        }
    }

    class SleeptimeListDiffUtil(): DiffUtil.ItemCallback<Sleeptime>() {
        override fun areContentsTheSame(oldItem: Sleeptime, newItem: Sleeptime) = oldItem == newItem
        override fun areItemsTheSame(oldItem: Sleeptime, newItem: Sleeptime) = oldItem.sleepTimeId == newItem.sleepTimeId
    }
}