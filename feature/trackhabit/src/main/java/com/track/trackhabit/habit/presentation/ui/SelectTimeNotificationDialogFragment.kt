package com.track.trackhabit.habit.presentation.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.presentation.constpackage.Const
import com.track.trackhabit.habit.presentation.constpackage.ConstRequestCode
import kotlinx.android.synthetic.main.dialog_select_time_notification.*
import timber.log.Timber
import java.util.*

class SelectTimeNotificationDialogFragment: DialogFragment() {
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
        return inflater.inflate(R.layout.dialog_select_time_notification, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmService = AlarmService(requireContext())

        check_monday.setOnClickListener {
            monday = check_monday.isChecked
        }

        check_tuesday.setOnClickListener {
            tuesday = check_tuesday.isChecked
        }

        check_wednesday.setOnClickListener {
            wednesday = check_wednesday.isChecked
        }

        check_thursday.setOnClickListener {
            thursday = check_thursday.isChecked
        }

        check_friday.setOnClickListener {
            friday = check_friday.isChecked
        }

        check_saturday.setOnClickListener {
            saturday = check_saturday.isChecked
        }
        check_sunday.setOnClickListener {
            sunday = check_sunday.isChecked

        }

        buttonOK.setOnClickListener {
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
            dismiss()
        }

        buttonCancel.setOnClickListener {
            dismiss()
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