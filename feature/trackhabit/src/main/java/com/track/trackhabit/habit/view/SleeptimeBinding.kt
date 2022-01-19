package com.track.trackhabit.habit.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Sleeptime

@BindingAdapter("app:sleeptime")
fun setSuggestTime(recyclerView: RecyclerView?, suggestTime: List<Sleeptime>?) {
    if (recyclerView?.adapter != null && suggestTime != null) {
        (recyclerView.adapter as? ListAdapter<Sleeptime, *>)?.submitList(suggestTime)
    }
}

@BindingAdapter("app:habit")
fun listHabit(recyclerView: RecyclerView?, habit: List<Habit>?){
    if( recyclerView?.adapter != null && habit != null){
        (recyclerView.adapter as? ListAdapter<Habit,*>)?.submitList(habit)
    }
}
