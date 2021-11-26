package com.track.trackhabit.habit.presentation.ui

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.presentation.constpackage.Const
import kotlinx.android.synthetic.main.fragment_select_time_notification.*
import java.text.SimpleDateFormat
import java.util.*

class SelectTimeNotificationFragment: Fragment() {
    private lateinit var alarmService: AlarmService
    private var monday: Boolean = true
    private var tuesday: Boolean = true
    private var wednesday: Boolean = true
    private var thursday: Boolean = true
    private var friday: Boolean = true
    private var saturday: Boolean = true
    private var sunday: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_time_notification, container, false)
    }


    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_fragmentAddHabit_setTime.text = SimpleDateFormat("HH:mm").format(Date())
        alarmService = AlarmService(requireContext())

        toggleButton_fragmentAddHabit_checkMonday.setOnClickListener {
            monday = toggleButton_fragmentAddHabit_checkMonday.isChecked
        }

        toggleButton_fragmentAddHabit_checkTuesday.setOnClickListener {
            tuesday = toggleButton_fragmentAddHabit_checkTuesday.isChecked
        }

        toggleButton_fragmentAddHabit_checkWednesday.setOnClickListener {
            wednesday = toggleButton_fragmentAddHabit_checkWednesday.isChecked
        }

        toggleButton_fragmentAddHabit_checkThursday.setOnClickListener {
            thursday = toggleButton_fragmentAddHabit_checkThursday.isChecked
        }

        toggleButton_fragmentAddHabit_checkFriday.setOnClickListener {
            friday = toggleButton_fragmentAddHabit_checkFriday.isChecked
        }

        toggleButton_fragmentAddHabit_checkSaturday.setOnClickListener {
            saturday = toggleButton_fragmentAddHabit_checkSaturday.isChecked
        }
        toggleButton_fragmentAddHabit_checkSunday.setOnClickListener {
            sunday = toggleButton_fragmentAddHabit_checkSunday.isChecked

        }

        button_fragmentAddHabit_buttonDone.setOnClickListener {
            setAlarm {
                alarmService.setRepeating(it)
                val intent =  Intent(context, AlarmReceiver::class.java).apply {
                    action = Const.ACTION_SET_REPETITIVE_EXACT
                    putExtra("MONDAY", monday)
                    putExtra("TUESDAY", tuesday)
                    putExtra("WEDNESDAY", wednesday)
                    putExtra("THURSDAY", thursday)
                    putExtra("FRIDAY", friday)
                    putExtra("SATURDAY", saturday)
                    putExtra("SUNDAY", sunday)
//                    putExtra("TIME", it)
                }
                requireContext().sendBroadcast(intent)

            }
            findNavController().navigate(R.id.action_nav_addhabit_to_nav_home)

        }
        button_fragmentAddHabit_setTime.setOnClickListener {
            setAlarm {
                button_fragmentAddHabit_setTime.text = SimpleDateFormat("HH:mm").format(Date(it))
            }
        }
        button_fragmentAddHabit_cancel.setOnClickListener {
            findNavController().navigate(R.id.action_nav_addhabit_to_nav_home)
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