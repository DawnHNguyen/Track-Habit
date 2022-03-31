package com.track.trackhabit.habit.presentation.ui.home.edithabit

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentEditHabitBinding
import com.track.trackhabit.habit.domain.entity.Habit
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditHabitFragment : Fragment() {
    private val editHabitViewModel by viewModels<EditHabitViewModel>()

    private val safeArgs: EditHabitFragmentArgs by navArgs()

    private lateinit var binding: FragmentEditHabitBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        binding.viewModel = editHabitViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = safeArgs.habitId
        editHabitViewModel.getHabit(id)
        binding.textViewFragmentEditHabitNameError.visibility = View.GONE
        binding.buttonFragmentEditHabitSetTime.text = editHabitViewModel.time.value

        editHabitViewModel.time.observe(viewLifecycleOwner) {
            Timber.d("gia_tri ${it}}")
        }
        editHabitViewModel.habit.observe(viewLifecycleOwner) {
            Timber.d("da lay duoc gia tri cua ha bit ${editHabitViewModel.habit}")
        }

        setupToggleButton()

        binding.buttonFragmentEditHabitSetTime.setOnClickListener {
            setAlarm {
                binding.buttonFragmentEditHabitSetTime.text =
                    SimpleDateFormat("HH:mm").format(Date(it))
                Timber.d("gia_tri sau khi click${editHabitViewModel.time.value}}")
            }
        }


        binding.buttonFragmentEditHabitButtonDone.setOnClickListener {
            if (binding.textInputEditTextFragmentEditHabitName.text.isNullOrBlank()) {
                binding.textViewFragmentEditHabitNameError.visibility = View.VISIBLE
            } else {
                Timber.d("-->${editHabitViewModel.habit.value!!.time} -- ${id}")
                Timber.d("-->${editHabitViewModel.habit.value!!.performances}")

                editHabitViewModel.updateHabit(
                    Habit(
                        habitId = id,
                        title = binding.textInputEditTextFragmentEditHabitName.text.toString(),
                        editHabitViewModel.formatTimeFromStringToDate(),
                        editHabitViewModel.habit.value!!.performances,
                        editHabitViewModel.getFrequency()
                    )
                )
                findNavController().navigate(R.id.action_nav_edithabit_to_nav_home)
            }

        }

        binding.buttonFragmentEditHabitCancel.setOnClickListener {
            findNavController().navigate(R.id.action_nav_edithabit_to_nav_home)
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun setupToggleButton() {
        with(binding) {
            toggleButtonFragmentEditHabitCheckMonday.setOnClickListener {
                editHabitViewModel.monday.value = !(editHabitViewModel.monday.value ?: true)
            }

            toggleButtonFragmentEditHabitCheckTuesday.setOnClickListener {
                editHabitViewModel.tuesday.value = !(editHabitViewModel.tuesday.value ?: true)
            }

            toggleButtonFragmentEditHabitCheckWednesday.setOnClickListener {
                editHabitViewModel.wednesday.value = !(editHabitViewModel.wednesday.value ?: true)
            }

            toggleButtonFragmentEditHabitCheckThursday.setOnClickListener {
                editHabitViewModel.thursday.value = !(editHabitViewModel.thursday.value ?: true)
            }

            toggleButtonFragmentEditHabitCheckFriday.setOnClickListener {
                editHabitViewModel.friday.value = !(editHabitViewModel.friday.value ?: true)
            }

            toggleButtonFragmentEditHabitCheckSaturday.setOnClickListener {
                editHabitViewModel.saturday.value = !(editHabitViewModel.saturday.value ?: true)
            }
            toggleButtonFragmentEditHabitCheckSunday.setOnClickListener {
                editHabitViewModel.sunday.value = !(editHabitViewModel.sunday.value ?: true)
            }
        }
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
}