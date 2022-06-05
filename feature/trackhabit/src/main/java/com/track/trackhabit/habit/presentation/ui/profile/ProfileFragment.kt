package com.track.trackhabit.habit.presentation.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.track.trackhabit.habit.databinding.FragmentProfileBinding

//@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uri = Uri.parse("https://forms.gle/Ee1fFzB42oGRnGdAA")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        binding.buttonProfileGiveFeedback.setOnClickListener {
            startActivity(intent)
        }
    }
}