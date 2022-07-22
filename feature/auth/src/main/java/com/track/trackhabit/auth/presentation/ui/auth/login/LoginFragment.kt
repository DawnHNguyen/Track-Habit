package com.track.trackhabit.auth.presentation.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.track.common.base.utils.hideKeyboard
import com.track.trackhabit.auth.R
import com.track.trackhabit.auth.databinding.FragmentLoginBinding
import com.track.trackhabit.auth.presentation.ui.auth.AuthViewModel
import com.track.trackhabit.habit.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.authViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.loginStateFlow.collect {
                when{
                    it.isError() -> Toast.makeText(context, it.error?.message.toString(), Toast.LENGTH_SHORT).show()
                    it.isSuccessful() -> {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
            }
        }

        binding.buttonLoginLogin.setOnClickListener {
            viewModel.login()
            hideKeyboard()
        }

        binding.parentView.setOnClickListener {
           hideKeyboard()
        }

        binding.buttonTextLoginRegistAccount.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_nav_register)
        }
    }
}