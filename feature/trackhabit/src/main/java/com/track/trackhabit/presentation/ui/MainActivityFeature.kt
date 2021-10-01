package com.track.trackhabit.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.track.trackhabit.R
import com.track.trackhabit.domain.entity.Habit
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivityFeature : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_feature)
        val recyclerView: RecyclerView = findViewById(R.id.test_recyclerView)
        val habitsListAdapter = HabitsListAdapter()
        val habitList = mutableListOf<Habit>()
        val habit = Habit(1, "Ngủ sớm", "", time = Date (12), listOf())
        habitList.add(habit)
        habitsListAdapter.submitList(habitList)
        recyclerView.adapter = habitsListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}