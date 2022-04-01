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
    private val habitInfoViewModel by viewModels<HabitInfoViewModel>()

    private val safeArgs: HabitInfoFragmentArgs by navArgs()

    private lateinit var binding: FragmentHabitInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitInfoBinding.inflate(inflater, container, false)
        binding.viewModel = habitInfoViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = safeArgs.habitId
        if (habitInfoViewModel.checkRoleWithId(id)) {
            habitInfoViewModel.getHabit(id)
            binding.buttonFragmentHabitInfoSetTime.text = habitInfoViewModel.time.value

            habitInfoViewModel.time.observe(viewLifecycleOwner) {
                Timber.d("gia_tri ${it}}")
            }
            habitInfoViewModel.habit.observe(viewLifecycleOwner) {
                Timber.d("da lay duoc gia tri cua ha bit ${habitInfoViewModel.habit}")
            }
        } else {
            habitInfoViewModel.time.value = SimpleDateFormat("HH:mm").format(Date())
        }

        binding.textViewFragmentHabitInfoNameError.visibility = View.GONE


        binding.buttonFragmentHabitInfoSetTime.setOnClickListener {
            setAlarm {
                binding.buttonFragmentHabitInfoSetTime.text =
                    SimpleDateFormat("HH:mm").format(Date(it))
                Timber.d("gia_tri sau khi click${habitInfoViewModel.time.value}}")
            }
        }

        binding.buttonFragmentHabitInfoButtonDone.setOnClickListener {
            if (habitInfoViewModel.checkRoleWithId(id)) {
                Timber.d("-->${habitInfoViewModel.habit.value!!.time} -- ${id}")
                Timber.d("-->${habitInfoViewModel.habit.value!!.performances}")

                habitInfoViewModel.updateHabit(id)
            } else {
                habitInfoViewModel.addHabit()

            }
            if (habitInfoViewModel.inputValidity.value == true) findNavController().navigate(R.id.action_nav_habitinfo_to_nav_home)
        }

        setupToggleButton()

        binding.buttonFragmentHabitInfoCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun setupToggleButton() {
        with(binding) {
            toggleButtonFragmentHabitInfoCheckMonday.setOnClickListener {
                habitInfoViewModel.monday.value = !(habitInfoViewModel.monday.value ?: true)
            }

            toggleButtonFragmentHabitInfoCheckTuesday.setOnClickListener {
                habitInfoViewModel.tuesday.value = !(habitInfoViewModel.tuesday.value ?: true)
            }

            toggleButtonFragmentHabitInfoCheckWednesday.setOnClickListener {
                habitInfoViewModel.wednesday.value = !(habitInfoViewModel.wednesday.value ?: true)
            }

            toggleButtonFragmentHabitInfoCheckThursday.setOnClickListener {
                habitInfoViewModel.thursday.value = !(habitInfoViewModel.thursday.value ?: true)
            }

            toggleButtonFragmentHabitInfoCheckFriday.setOnClickListener {
                habitInfoViewModel.friday.value = !(habitInfoViewModel.friday.value ?: true)
            }

            toggleButtonFragmentHabitInfoCheckSaturday.setOnClickListener {
                habitInfoViewModel.saturday.value = !(habitInfoViewModel.saturday.value ?: true)
            }
            toggleButtonFragmentHabitInfoCheckSunday.setOnClickListener {
                habitInfoViewModel.sunday.value = !(habitInfoViewModel.sunday.value ?: true)
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