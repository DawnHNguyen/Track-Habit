package com.track.trackhabit.habit.presentation.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.ActivityHomeBinding
import com.track.trackhabit.habit.domain.entity.Habit
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val recyclerView = binding.testRecyclerView
        val habitsListAdapter = HabitsListAdapter()
        val habitList = mutableListOf<Habit>()
        val habit = Habit(1, "Ngủ sớm", "", time = Date(12), listOf())
        val button: Button = findViewById(R.id.button)
        recyclerView.adapter = habitsListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        button.setOnClickListener {
            habitList.add(habit)
            habitsListAdapter.submitList(habitList)
        }
    }
}