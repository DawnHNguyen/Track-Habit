package com.track.trackhabit.habit.presentation.ui.home.addhabit

import android.annotation.SuppressLint
import android.app.Notification
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentAddHabitBinding
import com.track.trackhabit.habit.presentation.ui.AlarmService
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddHabitFragment : Fragment() {
    private val addHabitViewModel by viewModels<AddHabitViewModel>()
    private lateinit var alarmService: AlarmService
    private lateinit var binding: FragmentAddHabitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddHabitBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = addHabitViewModel
        return binding.root
    }


    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addHabitViewModel.timeHabit.value = SimpleDateFormat("HH:mm").format(Date())
        alarmService = AlarmService(requireContext())
        setupToggleButton()
        binding.buttonFragmentAddHabitSetTime.setOnClickListener {
            setAlarm {
                binding.buttonFragmentAddHabitSetTime.text =
                    SimpleDateFormat("HH:mm").format(Date(it))
            }
        }
        binding.buttonFragmentAddHabitCancel.setOnClickListener {
            findNavController().navigate(R.id.action_nav_addhabit_to_nav_home)
        }
    }

    private fun setupToggleButton() {
        with(binding) {
            toggleButtonFragmentAddHabitCheckMonday.setOnClickListener {
                addHabitViewModel.monday = binding.toggleButtonFragmentAddHabitCheckMonday.isChecked
            }

            toggleButtonFragmentAddHabitCheckTuesday.setOnClickListener {
                addHabitViewModel.tuesday =
                    binding.toggleButtonFragmentAddHabitCheckTuesday.isChecked
            }

            toggleButtonFragmentAddHabitCheckWednesday.setOnClickListener {
                addHabitViewModel.wednesday =
                    binding.toggleButtonFragmentAddHabitCheckWednesday.isChecked
            }

            toggleButtonFragmentAddHabitCheckThursday.setOnClickListener {
                addHabitViewModel.thursday =
                    binding.toggleButtonFragmentAddHabitCheckThursday.isChecked
            }

            toggleButtonFragmentAddHabitCheckFriday.setOnClickListener {
                addHabitViewModel.friday = binding.toggleButtonFragmentAddHabitCheckFriday.isChecked
            }

            toggleButtonFragmentAddHabitCheckSaturday.setOnClickListener {
                addHabitViewModel.saturday =
                    binding.toggleButtonFragmentAddHabitCheckSaturday.isChecked
            }
            toggleButtonFragmentAddHabitCheckSunday.setOnClickListener {
                addHabitViewModel.sunday = binding.toggleButtonFragmentAddHabitCheckSunday.isChecked
            }

            buttonFragmentAddHabitButtonDone.setOnClickListener {
                setVisibilityErrorMessage()
                addHabitViewModel.checkInputNullError()
                navigateOnClickDone()
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
    }

    private fun setVisibilityErrorMessage() {
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

    private fun navigateOnClickDone() {
        if (addHabitViewModel.inputValidity.value!!) findNavController().navigate(R.id.action_nav_addhabit_to_nav_home)
    }

    private fun setNotification(habitId: Int) {
        setAlarm {
            alarmService.setRepeating(it)
            val builder = NotificationCompat.Builder(
                requireContext(),
                getString(R.string.featureTrackhabit_channelId_notification)
            )
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.featureTrackhabit_title_notification))
                .setContentText(getString(R.string.featureTrackhabit_content_notification))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)

            with(NotificationManagerCompat.from(requireContext())) {
                notify(habitId, builder.build())
            }
        }
    }
}