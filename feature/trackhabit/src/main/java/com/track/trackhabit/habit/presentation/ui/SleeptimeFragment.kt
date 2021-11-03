package com.track.trackhabit.habit.presentation.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentSleeptimeBinding
import java.text.SimpleDateFormat
import java.util.*

class SleeptimeFragment : Fragment() {
    lateinit var binding: FragmentSleeptimeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sleeptime, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.textviewSleeptimeWaketimetitle.setOnClickListener{
           val cal = Calendar.getInstance()
           val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
               cal.set(Calendar.HOUR_OF_DAY, hour)
               cal.set(Calendar.MINUTE, minute)
               binding.textviewSleeptimeTime.text = SimpleDateFormat("HH:mm").format(cal.time)
           }
           TimePickerDialog(this.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
       }
    }
}