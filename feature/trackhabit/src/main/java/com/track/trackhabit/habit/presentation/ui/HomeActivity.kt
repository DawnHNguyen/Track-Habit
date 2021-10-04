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


        createChannel()

        setAlarm()

        val textView = findViewById<TextView>(R.id.button_testNotification)
        textView.setOnClickListener {
            Log.d("done","da hien")
            val builder = NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm manager")
                .setContentText("This is an alarm")
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)

            with(NotificationManagerCompat.from(this)) {
                notify(12, builder.build())
            }
    }



}

    private fun setAlarm() {
        alarmMng = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this,0,intent,0)
        }

        alarmMng.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 20*1000,
            alarmIntent
        )
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("channel_id","name", importance).apply {
                description = "dong mo ta nay that vo dung"
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}