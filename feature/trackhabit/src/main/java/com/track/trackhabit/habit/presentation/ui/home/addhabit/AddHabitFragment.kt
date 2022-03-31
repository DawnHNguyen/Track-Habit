package com.track.trackhabit.habit.presentation.ui.home.addhabit

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentEditHabitBinding
import com.track.trackhabit.habit.presentation.ui.AlarmService
import com.track.trackhabit.habit.presentation.ui.home.edithabit.EditHabitViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddHabitFragment : Fragment() {
    private val addHabitViewModel by viewModels<EditHabitViewModel>()
    private lateinit var alarmService: AlarmService
    private lateinit var binding: FragmentEditHabitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = addHabitViewModel
        return binding.root
    }


    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addHabitViewModel.time.value = SimpleDateFormat("HH:mm").format(Date())
        alarmService = AlarmService(requireContext())
        setupToggleButton()
        binding.buttonFragmentEditHabitSetTime.setOnClickListener {
            setAlarm {
                binding.buttonFragmentEditHabitSetTime.text =
                    SimpleDateFormat("HH:mm").format(Date(it))
                Timber.d("gia_tri ${addHabitViewModel.time.value}}")
            }
        }
        binding.buttonFragmentEditHabitCancel.setOnClickListener {
            findNavController().navigate(R.id.action_nav_addhabit_to_nav_home)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setupToggleButton() {
        with(binding) {
            toggleButtonFragmentEditHabitCheckMonday.setOnClickListener {
                addHabitViewModel.monday.value = !(addHabitViewModel.monday.value?:true)
            }

            toggleButtonFragmentEditHabitCheckTuesday.setOnClickListener {
                addHabitViewModel.tuesday.value = !(addHabitViewModel.tuesday.value?:true)
            }

            toggleButtonFragmentEditHabitCheckWednesday.setOnClickListener {
                addHabitViewModel.wednesday.value = !(addHabitViewModel.wednesday.value?:true)
            }

            toggleButtonFragmentEditHabitCheckThursday.setOnClickListener {
                addHabitViewModel.thursday.value = !(addHabitViewModel.thursday.value?:true)
            }

            toggleButtonFragmentEditHabitCheckFriday.setOnClickListener {
                addHabitViewModel.friday.value = !(addHabitViewModel.friday.value?: true)
            }

            toggleButtonFragmentEditHabitCheckSaturday.setOnClickListener {
                addHabitViewModel.saturday.value = !(addHabitViewModel.saturday.value?: true)
            }
            toggleButtonFragmentEditHabitCheckSunday.setOnClickListener {
                addHabitViewModel.sunday.value = !(addHabitViewModel.sunday.value?:true)
            }

            buttonFragmentEditHabitButtonDone.setOnClickListener {
                setVisibilityErrorMessage()
                addHabitViewModel.addHabit()
                navigateOnClickDone()
            }

        }
    }

    private fun setVisibilityErrorMessage() {
        binding.textViewFragmentEditHabitNameError.visibility = View.GONE
    }

    private fun setAlarm(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
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
        }
    }

    private fun navigateOnClickDone() {
        if (addHabitViewModel.inputValidity.value!!) findNavController().navigate(R.id.action_nav_addhabit_to_nav_home)
    }
}