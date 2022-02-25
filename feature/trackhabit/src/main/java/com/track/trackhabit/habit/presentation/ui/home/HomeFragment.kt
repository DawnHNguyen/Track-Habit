package com.track.trackhabit.habit.presentation.ui.home

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentHomeBinding
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.presentation.ui.AlarmService
import com.track.trackhabit.habit.presentation.ui.HabitsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var alarmService: AlarmService

    private lateinit var binding: FragmentHomeBinding

    private val habitsListAdapter = HabitsListAdapter(object: OnClickRevealButton{
        override fun onClickEdit(habit: Habit) {
            val habitId =habit.habitId
            val action = HomeFragmentDirections.actionNavHomeToNavEdithabit(habitId)
            Timber.d("--${action}")
            findNavController().navigate(action)
        }

        override fun onClickDelete(habit: Habit) {
            alarmService.setCancelAlarm(habit.habitId)
            homeViewModel.deleteHabit(habit)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.habits = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmService = AlarmService(requireContext())

        val recyclerView = binding.recyclerViewActivityHomeHabitList
        recyclerView.adapter = habitsListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.fabActivityHomeAddHabitButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_addhabit)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        habitsListAdapter.saveStates(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        habitsListAdapter.restoreStates(savedInstanceState)
    }
}