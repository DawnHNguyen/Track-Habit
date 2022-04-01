package com.track.trackhabit.habit.presentation.ui.home.habitinfo

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
import com.track.trackhabit.habit.databinding.FragmentHabitInfoBinding
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.presentation.constpackage.Const
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HabitInfoFragment : Fragment() {
    private val editHabitViewModel by viewModels<HabitInfoViewModel>()

    private val safeArgs: HabitInfoFragmentArgs by navArgs()

    private lateinit var binding: FragmentHabitInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitInfoBinding.inflate(inflater, container, false)
        binding.viewModel = editHabitViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = safeArgs.habitId
        val role = safeArgs.roleFragment
        if (role == Const.ROLE_EDIT && id != -1) {
            editHabitViewModel.getHabit(id)
            binding.buttonFragmentHabitInfoSetTime.text = editHabitViewModel.time.value

            editHabitViewModel.time.observe(viewLifecycleOwner) {
                Timber.d("gia_tri ${it}}")
            }
            editHabitViewModel.habit.observe(viewLifecycleOwner) {
                Timber.d("da lay duoc gia tri cua ha bit ${editHabitViewModel.habit}")
            }
        } else if (role == Const.ROLE_ADD && id == -1) {
            editHabitViewModel.time.value = SimpleDateFormat("HH:mm").format(Date())
        }

        binding.textViewFragmentHabitInfoNameError.visibility = View.GONE


        binding.buttonFragmentHabitInfoSetTime.setOnClickListener {
            setAlarm {
                binding.buttonFragmentHabitInfoSetTime.text =
                    SimpleDateFormat("HH:mm").format(Date(it))
                Timber.d("gia_tri sau khi click${editHabitViewModel.time.value}}")
            }
        }


        binding.buttonFragmentHabitInfoButtonDone.setOnClickListener {
            if (role == Const.ROLE_EDIT && id != -1) {
                Timber.d("-->${editHabitViewModel.habit.value!!.time} -- ${id}")
                Timber.d("-->${editHabitViewModel.habit.value!!.performances}")

                editHabitViewModel.updateHabit(
                    Habit(
                        habitId = id,
                        title = binding.textInputEditTextFragmentHabitInfoName.text.toString(),
                        editHabitViewModel.formatTimeFromStringToDate(),
                        editHabitViewModel.habit.value!!.performances,
                        editHabitViewModel.getFrequency()
                    )
                )
            } else if (role == Const.ROLE_ADD && id == -1) {
                editHabitViewModel.addHabit()

            }
            if (editHabitViewModel.inputValidity.value == true) findNavController().navigate(R.id.action_nav_habitinfo_to_nav_home)
        }

        setupToggleButton()

        binding.buttonFragmentHabitInfoCancel.setOnClickListener {
            findNavController().navigate(R.id.action_nav_habitinfo_to_nav_home)
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun setupToggleButton() {
        with(binding) {
            toggleButtonFragmentHabitInfoCheckMonday.setOnClickListener {
                editHabitViewModel.monday.value = !(editHabitViewModel.monday.value ?: true)
            }

            toggleButtonFragmentHabitInfoCheckTuesday.setOnClickListener {
                editHabitViewModel.tuesday.value = !(editHabitViewModel.tuesday.value ?: true)
            }

            toggleButtonFragmentHabitInfoCheckWednesday.setOnClickListener {
                editHabitViewModel.wednesday.value = !(editHabitViewModel.wednesday.value ?: true)
            }

            toggleButtonFragmentHabitInfoCheckThursday.setOnClickListener {
                editHabitViewModel.thursday.value = !(editHabitViewModel.thursday.value ?: true)
            }

            toggleButtonFragmentHabitInfoCheckFriday.setOnClickListener {
                editHabitViewModel.friday.value = !(editHabitViewModel.friday.value ?: true)
            }

            toggleButtonFragmentHabitInfoCheckSaturday.setOnClickListener {
                editHabitViewModel.saturday.value = !(editHabitViewModel.saturday.value ?: true)
            }
            toggleButtonFragmentHabitInfoCheckSunday.setOnClickListener {
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