package com.track.trackhabit.habit.presentation.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.ActivityHomeBinding
import com.track.trackhabit.habit.domain.entity.Habit
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var alarmMng: AlarmManager
    private lateinit var alarmIntent: PendingIntent

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val recyclerView = binding.testRecyclerView
        val habitsListAdapter = HabitsListAdapter()
        val habitList = mutableListOf<Habit>()
        val habit = Habit(1, "Ngủ sớm", "", time = Date(12), listOf())
        val button: Button = findViewById(R.id.button)
        recyclerView.adapter = habitsListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        button.setOnClickListener {
            habitList.add(habit)
            habitsListAdapter.submitList(habitList)
        }


        createChannel( this)
        alarmMng = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this,0,intent,0)
        }

        val cancelNoti = findViewById<Button>(R.id.button_canceltNotification)
        val setNoti = findViewById<Button>(R.id.button_setNotification)
        setNoti.setOnClickListener {

            setAlarm()

            val builder = NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm manager")
                .setContentText("Đã đặt báo thức cho bạn")
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)

            with(NotificationManagerCompat.from(this)) {
                notify(12, builder.build())
            }
        }

        cancelNoti.setOnClickListener {
            alarmMng.cancel(alarmIntent)
            Toast.makeText(this,"đã hủy báo thức", Toast.LENGTH_LONG).show()
        }


    }

    private fun setAlarm() {

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 22)
            set(Calendar.MINUTE, 8)
            set(Calendar.SECOND, 0)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMng.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmIntent
            )
        }
        else{
            alarmMng.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmIntent
            )
        }
    }

}