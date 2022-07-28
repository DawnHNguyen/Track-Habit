package com.track.trackhabit.auth.presentation.ui.auth.register

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
import com.track.trackhabit.auth.databinding.FragmentVerifyEmailBinding
import com.track.trackhabit.auth.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyEmailFragment: Fragment() {

    private var _binding: FragmentVerifyEmailBinding? = null

    private val binding get() = _binding!!
    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerifyEmailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.authViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.verifyEmailStateFlow.collect {
                when {
                    it.isError() -> Toast.makeText(context, it.error?.message.toString(), Toast.LENGTH_SHORT).show()
                    it.isSuccessful() -> {
                        Toast.makeText(context, it.data?.message.toString(), Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_nav_verifyEmail_to_login)
                    }
                }
            }

            viewModel.getCodeStateFlow.collect {
                when {
                    it.isError() -> Toast.makeText(context, it.error?.message.toString(), Toast.LENGTH_SHORT).show()
                    it.isSuccessful() -> Toast.makeText(context, it.data?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonVerifyConfirm.setOnClickListener {
            viewModel.verifyEmail()
            hideKeyboard()
        }

        binding.textViewVerifyEmailGetCodeAgain.setOnClickListener {
            viewModel.getEmailToken()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}