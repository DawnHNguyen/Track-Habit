package com.track.trackhabit.habit.presentation.ui.home

import android.app.DatePickerDialog
import android.app.Notification
import android.app.TimePickerDialog
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentHomeBinding
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.presentation.ui.AlarmService
import com.track.trackhabit.habit.presentation.ui.HabitsListAdapter
import com.track.trackhabit.habit.presentation.ui.createChannel
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
            Timber.d("--${action}--${habitId}")
            findNavController().navigate(action)
        }

        override fun onClickDelete(habit: Habit) {
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

        val habitList = mutableListOf<Habit>()
        val habit = Habit(1, "Ngủ sớm", "", time = Date(12), listOf(), "1111111")
        recyclerView.adapter = habitsListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.button.setOnClickListener {
            habitList.add(habit)
            habitsListAdapter.submitList(habitList)
        }

        createChannel(requireContext())

        binding.buttonSetNotification.setOnClickListener {
            setAlarm {
                alarmService.setRepeating(it)
                val builder = NotificationCompat.Builder(requireContext(), "channel_id")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Alarm manager")
                    .setContentText("complete")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX)

                with(NotificationManagerCompat.from(requireContext())) {
                    notify(12, builder.build())
                }

            }

        }

        binding.buttonCancelNotification.setOnClickListener {
            alarmService.setCancelAlarm()
            Toast.makeText(requireContext(), "đã hủy báo thức", Toast.LENGTH_LONG).show()
        }

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

    private fun setAlarm(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            DatePickerDialog(
                requireContext(),
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                    TimePickerDialog(
                        requireContext(),
                        0,
                        { _, hour, minute ->
                            this.set(Calendar.HOUR_OF_DAY, hour)
                            this.set(Calendar.MINUTE, minute)
                            callback(this.timeInMillis)
                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        true
                    ).show()
                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}