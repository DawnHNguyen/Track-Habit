package com.track.trackhabit.habit.presentation.ui.sleeptime

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentSleepTimeBinding
import com.track.trackhabit.habit.databinding.SetRemindSleepDialogFragmentBinding
import com.track.trackhabit.habit.presentation.ui.OnClickBackSleeptime
import com.track.trackhabit.habit.presentation.ui.OnClickConfirmWaketime
import com.track.trackhabit.habit.presentation.ui.OnClickSuggestTimeRecyclerView
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
        binding.confirmWakeTimeListener = this
        binding.backListener = this
        setTimePicker()
        initializeSleeptimeRecyclerView()
        binding.buttonSleepTimeConfrimSleepTime.setOnClickListener {
            showDialogSetReminSleep()
        }
    }

    private fun initializeSleeptimeRecyclerView() {
        val recyclerView = binding.recyclerViewSleepTimeSuggestSleepTime
        recyclerView.itemAnimator = null
        val sleeptimeListAdapter = SleeptimeListAdapter(object : OnClickSuggestTimeRecyclerView {
            override fun onClickItemSugesstTime(clickedLoop: Int) {
                viewModel.setCofirmSleeptimeEnabled()
                viewModel.onClickItem(clickedLoop)
            }
        })
        recyclerView.adapter = sleeptimeListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setTimePicker() {
        binding.textViewSleepTimeWakeTime.setOnClickListener {
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
        viewModel.addListSleepTime()
        viewModel.onConfirmWaketimeUpdateVisibility()
    }

    override fun onClickBackButton() {
        with(viewModel) {
            clearListSuggestSleeptime()
            onBackUpdateVisibility()
            resetTimepicker()
            setConfirmSleeptimeDisable()
        }
    }

    private fun showDialogSetReminSleep() {
        val dialogBinding: SetRemindSleepDialogFragmentBinding? = DataBindingUtil.inflate(
            LayoutInflater.from(this.context),
            R.layout.set_remind_sleep_dialog_fragment,
            null,
            false
        )
        val dialog = AlertDialog.Builder(this.context).create()

        dialog.apply {
            setView(dialogBinding?.root)
            window?.setBackgroundDrawable(
                resources.getDrawable(
                    R.drawable.background_dialog_setremindsleep
                )
            )
        }.show()

        dialogBinding?.buttonDialogSetRemindSleepCancel?.setOnClickListener {
            dialog.cancel()
        }
//        dialogBinding?.buttonDialogSetRemindSleepOk?.setOnClickListener {
//            setupSleeptimeNoti()
//        }
    }

//    private fun setupSleeptimeNoti(){
//        var builder = NotificationCompat.Builder(this.requireContext(), R.string.sleepNoti_chanel_id.toString())
//            .setSmallIcon(R.drawable.notification_icon)
//            .setContentTitle(R.string.sleepNoti_title.toString())
//            .setContentText(R.string.sleepNoti_content.toString())
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//        val sleeptimeNotifyId = 1
//        with(NotificationManagerCompat.from(this.requireContext())){
//            notify(sleeptimeNotifyId, builder.build())
//        }
//    }
//
//    private fun createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = getString(R.string.sleepNoti_chanel_name)
//            val descriptionText = getString(R.string.sleepNoti_chanel_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(R.string.sleepNoti_chanel_id.toString(), name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService() as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
}