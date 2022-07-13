package com.track.trackhabit.habit.presentation.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.track.common.base.utils.hideKeyboard
import com.track.trackhabit.habit.databinding.FragmentLoginBinding
import com.track.trackhabit.habit.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.loginStateFlow.collect {
                if (viewModel.loginStateFlow.value.isError()) Toast.makeText(
                    context,
                    viewModel.loginStateFlow.value.error?.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.buttonFragmentLoginLogin.setOnClickListener {
            viewModel.login()
        }
        binding.parentView.setOnClickListener {
           activity?.hideKeyboard()
        }
    }
}