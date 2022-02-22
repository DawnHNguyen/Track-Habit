package com.track.trackhabit.habit.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentEditHabitBinding
import com.track.trackhabit.habit.domain.entity.Habit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_edit_habit.*
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class EditHabitFragment: Fragment() {
    private val editHabitViewModel by viewModels<EditHabitViewModel>()

    private val safeArgs: EditHabitFragmentArgs by navArgs()

    private lateinit var binding: FragmentEditHabitBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        binding.viewModel = editHabitViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = safeArgs.habitId
        editHabitViewModel.getHabit(id)
        binding.textViewFragmentEditHabitDescriptionError.visibility = View.GONE
        binding.textViewFragmentEditHabitNameError.visibility = View.GONE
        binding.buttonFragmentEditHabitSetTime.text = editHabitViewModel.time.value
        Timber.d("gia_tri ${editHabitViewModel.time.value}}")
        Timber.d("gia_tri -${editHabitViewModel.monday.value}}")
        Timber.d("gia_tri --${editHabitViewModel.tuesday.value}}")
        Timber.d("gia_tri ---${editHabitViewModel.friday.value}}")
        binding.buttonFragmentEditHabitButtonDone.setOnClickListener {
            Timber.d("-->${editHabitViewModel.habit.value!!.time} -- ${id}")
            Timber.d("-->${editHabitViewModel.habit.value!!.performances}")

            editHabitViewModel.updatehabit(Habit(habitId = id,
                title = binding.textInputEditTextFragmentEditHabitName.text.toString(),
                description = binding.textInputEditTextFragmentAddHabitDescription.text.toString(),
                editHabitViewModel.habit.value!!.time,
                editHabitViewModel.habit.value!!.performances,editHabitViewModel.getFrequency()))

            findNavController().navigate(R.id.action_nav_edithabit_to_nav_home)
        }
    }
}