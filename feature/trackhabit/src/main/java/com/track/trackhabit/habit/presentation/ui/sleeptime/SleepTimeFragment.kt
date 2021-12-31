package com.track.trackhabit.habit.presentation.ui.sleeptime

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.track.trackhabit.habit.databinding.FragmentSleepTimeBinding
import com.track.trackhabit.habit.presentation.ui.OnClickBackSleeptime
import com.track.trackhabit.habit.presentation.ui.OnClickConfirmWaketime
import com.track.trackhabit.habit.presentation.ui.SleeptimeListAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.text.SimpleDateFormat as SimpleDateFormat1

@AndroidEntryPoint
class SleepTimeFragment : Fragment(), OnClickConfirmWaketime, OnClickBackSleeptime {
    lateinit var binding: FragmentSleepTimeBinding
    val viewModel: SleepTimeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSleepTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.sleeptimeViewModel = viewModel
        binding.confirmSleepTimeListener = this
        binding.backListener = this
        setTimePicker()
        initializeSleeptimeRecyclerView()
    }

    private fun initializeSleeptimeRecyclerView() {
        val recyclerView = binding.recyclerviewSleeptimeSuggestsleeptime
        val sleeptimeListAdapter = SleeptimeListAdapter()
        recyclerView.adapter = sleeptimeListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setTimePicker() {
        binding.textviewSleeptimeWaketime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                viewModel.setWakeTime(SimpleDateFormat1("HH:mm").format(cal.time))
            }
            TimePickerDialog(
                this.context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    override fun onClickConfirmWaketime() {
        viewModel.onConfirmWaketimeUpdateVisibility()
        viewModel.addListSleepTime()
    }

    override fun onClickBackButton() {
        viewModel.clearListSuggestSleeptime()
        viewModel.onBackUpdateVisibility()
        viewModel.resetTimepicker()
    }
}