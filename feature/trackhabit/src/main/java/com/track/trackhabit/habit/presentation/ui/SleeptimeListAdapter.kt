package com.track.trackhabit.habit.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.ItemSleepTimeBinding
import com.track.trackhabit.habit.domain.entity.Sleeptime

class SleeptimeListAdapter(private val onClickSuggestTime: OnClickSuggestTimeRecyclerView) :
    ListAdapter<Sleeptime, SleeptimeListAdapter.SleeptimeListViewHolder>(SleeptimeListDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SleeptimeListViewHolder = SleeptimeListViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: SleeptimeListViewHolder,
        position: Int,
    ) {
        val sleeptime = getItem(position)
        holder.bind(sleeptime, onClickSuggestTime)
    }

    class SleeptimeListViewHolder private constructor(private val binding: ItemSleepTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sleepTime: Sleeptime, item: OnClickSuggestTimeRecyclerView) {
            binding.itemListener = item
            binding.sleeptime = sleepTime
            binding.executePendingBindings()
            if (sleepTime.isClicked) {
                binding.itemSleepTime.background =
                    binding.root.context.getDrawable(R.drawable.background_itemsleeptime_clicked)
                binding.textViewItemSleepTimeSleepTime.setTextColor(
                    binding.root.context.resources.getColor(
                        R.color.white
                    )
                )
                binding.textViewItemSleepTimeLoop.setTextColor(
                    binding.root.context.resources.getColor(
                        R.color.white
                    )
                )
            } else {
                binding.itemSleepTime.background =
                    binding.root.context.getDrawable(R.drawable.background_itemsleeptime_default)
                binding.textViewItemSleepTimeSleepTime.setTextColor(
                    binding.root.context.resources.getColor(
                        R.color.black
                    )
                )
                binding.textViewItemSleepTimeLoop.setTextColor(
                    binding.root.context.resources.getColor(
                        R.color.black
                    )
                )
            }
        }

        companion object {
            fun from(parent: ViewGroup): SleeptimeListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSleepTimeBinding.inflate(layoutInflater, parent, false)
                return SleeptimeListViewHolder(binding)
            }
        }
    }

    class SleeptimeListDiffUtil : DiffUtil.ItemCallback<Sleeptime>() {
        override fun areContentsTheSame(oldItem: Sleeptime, newItem: Sleeptime) = oldItem == newItem
        override fun areItemsTheSame(oldItem: Sleeptime, newItem: Sleeptime) =
            oldItem.sleepTimeId == newItem.sleepTimeId
    }
}