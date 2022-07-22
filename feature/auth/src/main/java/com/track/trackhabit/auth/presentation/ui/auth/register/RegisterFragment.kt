package com.track.trackhabit.auth.presentation.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.track.trackhabit.auth.R
import com.track.trackhabit.auth.databinding.FragmentRegisterBinding


class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonTextRegisterLoginAccount.setOnClickListener {
            findNavController().navigate(R.id.action_nav_register_to_login)
        }

        binding.buttonRegisterRegister.setOnClickListener{
            if (binding.textInputRegisterEmail.text.toString().isNotEmpty()
                && binding.textInputRegisterUsername.text.toString().isNotEmpty()
                && binding.textInputRegisterPassword.text.toString().isNotEmpty()
                && binding.textInputRegisterConfirmPass.text.toString().isNotEmpty()
                && binding.textInputRegisterFullname.text.toString().isNotEmpty())
            findNavController().navigate(R.id.action_nav_register_to_nav_verifyEmail)
        }
    }
}