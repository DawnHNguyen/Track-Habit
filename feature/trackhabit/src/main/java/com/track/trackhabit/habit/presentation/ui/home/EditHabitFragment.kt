package com.track.trackhabit.habit.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.track.trackhabit.habit.databinding.FragmentEditHabitBinding
import dagger.hilt.android.AndroidEntryPoint

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
    }
}