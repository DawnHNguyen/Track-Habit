package com.track.trackhabit.habit.presentation.ui

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentSelectTimeNotificationBinding
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.domain.entity.Inspection
import com.track.trackhabit.habit.domain.entity.local.HabitLocal
import com.track.trackhabit.habit.presentation.ui.home.SelectTimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_select_time_notification.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SelectTimeNotificationFragment: Fragment() {
    private val selectTimeViewModel by viewModels<SelectTimeViewModel>()
    private lateinit var alarmService: AlarmService
    private lateinit var binding: FragmentSelectTimeNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectTimeNotificationBinding.inflate(inflater,container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = selectTimeViewModel
        return binding.root
    }



    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectTimeViewModel.timeHabit.value = SimpleDateFormat("HH:mm").format(Date())
        alarmService = AlarmService(requireContext())

        binding.toggleButtonFragmentAddHabitCheckMonday.setOnClickListener {
            selectTimeViewModel.monday = binding.toggleButtonFragmentAddHabitCheckMonday.isChecked
        }

        binding.toggleButtonFragmentAddHabitCheckTuesday.setOnClickListener {
            selectTimeViewModel.tuesday = binding.toggleButtonFragmentAddHabitCheckTuesday.isChecked
        }

        binding.toggleButtonFragmentAddHabitCheckWednesday.setOnClickListener {
            selectTimeViewModel.wednesday = binding.toggleButtonFragmentAddHabitCheckWednesday.isChecked
        }

        binding.toggleButtonFragmentAddHabitCheckThursday.setOnClickListener {
            selectTimeViewModel.thursday = binding.toggleButtonFragmentAddHabitCheckThursday.isChecked
        }

        binding.toggleButtonFragmentAddHabitCheckFriday.setOnClickListener {
            selectTimeViewModel.friday = binding.toggleButtonFragmentAddHabitCheckFriday.isChecked
        }

        binding.toggleButtonFragmentAddHabitCheckSaturday.setOnClickListener {
            selectTimeViewModel.saturday = binding.toggleButtonFragmentAddHabitCheckSaturday.isChecked
        }
        binding.toggleButtonFragmentAddHabitCheckSunday.setOnClickListener {
            selectTimeViewModel.sunday = binding.toggleButtonFragmentAddHabitCheckSunday.isChecked
        }

        binding.buttonFragmentAddHabitButtonDone.setOnClickListener {
            setVisibilityErrorMessage()
            if (binding.textInputEditTextFragmentAddHabitName.text.isNullOrBlank()  || binding.textInputEditTextFragmentAddHabitDescription.text.isNullOrBlank()) {
                if(binding.textInputEditTextFragmentAddHabitName.text.isNullOrBlank()){
                    binding.textViewFragmentAddHabitNameError.visibility = View.VISIBLE
                }
                if (binding.textInputEditTextFragmentAddHabitDescription.text.isNullOrBlank()){
                    binding.textViewFragmentAddHabitDescriptionError.visibility = View.VISIBLE
                }
            }
            else{

                var frequency = selectTimeViewModel.getFrequency()
                Log.d("check_databinding","${selectTimeViewModel.nameHabit.value} + ${selectTimeViewModel.descriptionHabit.value} + ${binding.buttonFragmentAddHabitSetTime.text} + ${frequency}")
                selectTimeViewModel.addHabit(HabitLocal(0,
                    selectTimeViewModel.nameHabit.value.toString(),
                    selectTimeViewModel.descriptionHabit.value.toString(),
                    selectTimeViewModel.changeToTime(binding.buttonFragmentAddHabitSetTime.text.toString()),
                    frequency).mapToDomainModel())
                findNavController().navigate(R.id.action_nav_addhabit_to_nav_home)
            }


//                alarmService.setRepeating(it)
//                val intent =  Intent(context, AlarmReceiver::class.java).apply {
//                    action = Const.ACTION_SET_REPETITIVE_EXACT
//                    putExtra("MONDAY", monday)
//                    putExtra("TUESDAY", tuesday)
//                    putExtra("WEDNESDAY", wednesday)
//                    putExtra("THURSDAY", thursday)
//                    putExtra("FRIDAY", friday)
//                    putExtra("SATURDAY", saturday)
//                    putExtra("SUNDAY", sunday)
////                    putExtra("TIME", it)
//                }
//                requireContext().sendBroadcast(intent)


        }
        binding.buttonFragmentAddHabitSetTime.setOnClickListener {
            setAlarm {
                binding.buttonFragmentAddHabitSetTime.text = SimpleDateFormat("HH:mm").format(Date(it))
            }
        }
        binding.buttonFragmentAddHabitCancel.setOnClickListener {
            findNavController().navigate(R.id.action_nav_addhabit_to_nav_home)
        }
    }

    private fun setVisibilityErrorMessage(){
        binding.textViewFragmentAddHabitNameError.visibility = View.GONE
        binding.textViewFragmentAddHabitDescriptionError.visibility = View.GONE
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