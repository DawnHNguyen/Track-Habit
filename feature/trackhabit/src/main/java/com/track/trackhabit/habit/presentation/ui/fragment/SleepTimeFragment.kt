package com.track.trackhabit.habit.presentation.ui.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.track.trackhabit.habit.databinding.FragmentSleepTimeBinding
import com.track.trackhabit.habit.domain.entity.SleepDuration
import com.track.trackhabit.habit.domain.entity.Sleeptime
import com.track.trackhabit.habit.presentation.ui.SleeptimeListAdapter
import java.util.*
import java.text.SimpleDateFormat as SimpleDateFormat1

class SleepTimeFragment : Fragment() {
    lateinit var binding: FragmentSleepTimeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSleepTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerviewSleeptimeSuggestsleeptime
        val sleeptimeListAdapter = SleeptimeListAdapter()
        val sleepTimeList = mutableListOf<Sleeptime>()
        binding.textviewSleeptimeTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                binding.textviewSleeptimeTime.text = SimpleDateFormat1("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                this.context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        recyclerView.adapter = sleeptimeListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.buttonSleeptimeConfirmwaketime.setOnClickListener {
            binding.buttonSleeptimeConfirmwaketime.visibility = View.GONE
            binding.textviewSleeptimeSleeptimetitle.visibility = View.VISIBLE
            val wakeTime = binding.textviewSleeptimeTime.text
            val sleepTime6Loop = Sleeptime(
                1,
                sleepTime = calSleepTime(wakeTime.toString(), SleepDuration.Six.sleepDurationString),
                "Ngủ " + SleepDuration.Six.sleepDurationDouble.toString() + " tiếng - " + SleepDuration.Six.loop.toString() + " chu kỳ giấc ngủ"
            )
            val sleepTime5Loop = Sleeptime(
                2,
                sleepTime = calSleepTime(wakeTime.toString(), SleepDuration.Five.sleepDurationString),
                "Ngủ " + SleepDuration.Five.sleepDurationDouble.toString() + " tiếng - " + SleepDuration.Five.loop.toString() + " chu kỳ giấc ngủ"
            )
            val sleepTime4Loop = Sleeptime(
                3,
                sleepTime = calSleepTime(wakeTime.toString(), SleepDuration.Four.sleepDurationString),
                "Ngủ " + SleepDuration.Four.sleepDurationDouble.toString() + " tiếng - " + SleepDuration.Four.loop.toString() + " chu kỳ giấc ngủ"
            )
            val sleepTime3Loop = Sleeptime(
                4,
                sleepTime = calSleepTime(wakeTime.toString(), SleepDuration.Three.sleepDurationString),
                "Ngủ " + SleepDuration.Three.sleepDurationDouble.toString() + " tiếng - " + SleepDuration.Three.loop.toString() + " chu kỳ giấc ngủ"
            )
            sleepTimeList.add(sleepTime6Loop)
            sleepTimeList.add(sleepTime5Loop)
            sleepTimeList.add(sleepTime4Loop)
            sleepTimeList.add(sleepTime3Loop)
            sleeptimeListAdapter.submitList(sleepTimeList)
        }
    }

    private fun calSleepTime(wakeTime: String, sleepDuration: String): String {
        var sleepTime: String
        val arrayWake = wakeTime.split(':').run {
            IntArray(size) {
                get(it).toInt()
            }
        }
        val wakeMin = arrayWake[1]
        var wakeHour = arrayWake[0]

        val arrayDuration = sleepDuration.split(':').run {
            IntArray(size) {
                get(it).toInt()
            }
        }
        val durationMin = arrayDuration[1]
        val durationHour = arrayDuration[0]
        val sleepMin: Int
        if (wakeMin < durationMin) {
            sleepMin = 60 - (durationMin - wakeMin)
            wakeHour -= 1
        } else sleepMin = wakeMin - durationMin

        val sleepHour: Int = if (wakeHour < durationHour) 24 - (durationHour - wakeHour) else wakeHour - durationHour
        sleepTime = if (sleepHour < 10) "0$sleepHour" else  "$sleepHour"
        sleepTime = if (sleepMin < 10) "$sleepTime:0$sleepMin" else "$sleepTime:$sleepMin"
        return sleepTime
    }
}