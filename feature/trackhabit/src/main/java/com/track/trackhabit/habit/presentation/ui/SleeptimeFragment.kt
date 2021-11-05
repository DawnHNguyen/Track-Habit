package com.track.trackhabit.habit.presentation.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.track.trackhabit.habit.databinding.FragmentSleepTimeBinding
import java.text.SimpleDateFormat
import java.util.*
class SleeptimeFragment : Fragment() {
    lateinit var binding: FragmentSleepTimeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSleepTimeBinding.inflate(inflater, container, false)
        return binding.root
        Log.d("Creat view", "Navigated to sleep time fragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.textviewSleeptimeTime.setOnClickListener{
           val cal = Calendar.getInstance()
           val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
               cal.set(Calendar.HOUR_OF_DAY, hour)
               cal.set(Calendar.MINUTE, minute)
               binding.textviewSleeptimeTime.text = SimpleDateFormat("HH:mm").format(cal.time)
           }
           TimePickerDialog(this.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            Log.d("clickTime", "onClick time textView")
       }
    }
}