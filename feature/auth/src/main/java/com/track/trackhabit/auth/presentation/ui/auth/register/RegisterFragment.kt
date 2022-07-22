package com.track.trackhabit.auth.presentation.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isNotEmpty
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.track.common.base.utils.hideKeyboard
import com.track.trackhabit.auth.R
import com.track.trackhabit.auth.databinding.FragmentRegisterBinding
import com.track.trackhabit.auth.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.authViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonTextRegisterLoginAccount.setOnClickListener {
            findNavController().navigate(R.id.action_nav_register_to_login)
        }
//      xử lý navigation chuyển sang màn verify
        binding.buttonRegisterRegister.setOnClickListener{
            if (binding.textInputRegisterEmail.text.toString().isNotEmpty()
                && binding.textInputRegisterUsername.text.toString().isNotEmpty()
                && binding.textInputRegisterPassword.text.toString().isNotEmpty()
                && binding.textInputRegisterConfirmPass.text.toString().isNotEmpty()
                && binding.textInputRegisterFullname.text.toString().isNotEmpty())
            findNavController().navigate(R.id.action_nav_register_to_nav_verifyEmail)
        }

        lifecycleScope.launch {
            viewModel.registerStateFlow.collect {
                if (it.isError()) Toast.makeText(context, it.error?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonRegisterRegister.setOnClickListener {
            viewModel.register()
            hideKeyboard()
        }

        binding.parentView.setOnClickListener {
            hideKeyboard()
        }

    }
}