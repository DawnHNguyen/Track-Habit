package com.track.trackhabit.habit.presentation.ui

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.dialog_select_time_notification.*
import timber.log.Timber

class SelectTimeNotificationDialogFragment: DialogFragment() {
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
            Log.d("checkIntent","Intent will have ${monday} - ${tuesday} - ${wednesday} - ${thursday} - ${friday} - ${saturday} - ${sunday}")

            val intent =  Intent(context, AlarmReceiver::class.java)
            intent.putExtra("MONDAY", monday)
                .putExtra("TUESDAY", tuesday)
                .putExtra("WEDNESDAY", wednesday)
                .putExtra("THURSDAY", thursday)
                .putExtra("FRIDAY", friday)
                .putExtra("SATURDAY", saturday)
                .putExtra("SUNDAY", sunday)

            dismiss()
        }

        buttonCancel.setOnClickListener {
            dismiss()
        }
    }

}