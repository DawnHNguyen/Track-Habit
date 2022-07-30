package com.track.trackhabit.habit.presentation.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orhanobut.hawk.Hawk
import com.track.common.base.constpackage.HawkKey
import com.track.navigation.navigateToAuthActivity
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.FragmentProfileBinding
import com.track.trackhabit.habit.domain.entity.LanguageCode
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel by viewModels<ProfileViewModel>()

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
        binding.switchButtonProfileLanguage.isChecked =
            resources.configuration.locale == Locale("en")
        binding.switchButtonProfileLanguage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) changeLanguage(LanguageCode.US.languageCode) else changeLanguage(LanguageCode.VN.languageCode)
        }

        binding.buttonProfileLogOutLogIn.setOnClickListener {
            navigateToAuthActivity(requireContext())
            activity?.finish()
        }

        binding.buttonProfileLogOutLogIn.text = if (Hawk.get(HawkKey.IS_USE_ACC)) getString(R.string.profile_logout_button) else getString(R.string.profile_login_button)

    }

    private fun changeLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        resources.configuration.setLocale(locale)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
        profileViewModel.addLanguagePref(languageCode)
        activity?.finish()
        startActivity(activity?.intent)
    }
}