package com.track.trackhabit.habit.presentation.ui.home

import android.app.DatePickerDialog
import android.app.Notification
import android.app.TimePickerDialog
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentHomeBinding
import com.track.trackhabit.habit.domain.entity.Habit
import com.track.trackhabit.habit.presentation.ui.AlarmService
import com.track.trackhabit.habit.presentation.ui.HabitsListAdapter
import com.track.trackhabit.habit.presentation.ui.createChannel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var alarmService: AlarmService

    private lateinit var binding: FragmentHomeBinding

    private val habitsListAdapter = HabitsListAdapter()

    private val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {



        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            if (direction == ItemTouchHelper.LEFT) {
                habitsListAdapter.notifyItemChanged(position)
                Toast.makeText(requireContext(), "Swipe left", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                val itemView = viewHolder.itemView
                val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                val width = height / 3


//                if (dX < 0) {
//                    var paint.color = Color.RED
//                    val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
//                    c.drawRect(background, paint)
//                    val icon = BitmapFactory.decodeResource(this@HomeFragment.resources, R.drawable.ic_statistical)
//                    val margin = (dX / 5 - width) / 2
//                    val iconDest = RectF(itemView.right.toFloat() + margin, itemView.top.toFloat() + width, itemView.right.toFloat() + (margin + width), itemView.bottom.toFloat() - width)
//                    c.drawBitmap(icon, null, iconDest, paint)
//                } else {
//                    c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
//                }
            } else {
                c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX / 5, dY, actionState, isCurrentlyActive)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.habits = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmService = AlarmService(requireContext())

        val recyclerView = binding.recyclerViewActivityHomeHabitList

        val habitList = mutableListOf<Habit>()
        val habit = Habit(1, "Ngủ sớm", "", time = Date(12), listOf(), "1111111")
        recyclerView.adapter = habitsListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        binding.button.setOnClickListener {
            habitList.add(habit)
            habitsListAdapter.submitList(habitList)
        }


        createChannel(requireContext())

        binding.buttonSetNotification.setOnClickListener {
            setAlarm {
                alarmService.setRepeating(it)
                val builder = NotificationCompat.Builder(requireContext(), "channel_id")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Alarm manager")
                    .setContentText("complete")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX)

                with(NotificationManagerCompat.from(requireContext())) {
                    notify(12, builder.build())
                }

            }

        }

        binding.buttonCancelNotification.setOnClickListener {
            alarmService.setCancelAlarm()
            Toast.makeText(requireContext(), "đã hủy báo thức", Toast.LENGTH_LONG).show()
        }

        binding.fabActivityHomeAddHabitButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_addhabit)
        }
    }

    private fun setAlarm(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            DatePickerDialog(
                requireContext(),
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
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
                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}