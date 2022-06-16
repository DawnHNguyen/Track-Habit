package com.track.trackhabit.habit.presentation.ui.profile

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.track.trackhabit.habit.databinding.FragmentProfileBinding
import com.track.trackhabit.habit.presentation.constpackage.Const
import com.track.trackhabit.habit.presentation.ui.HomeActivity
import java.util.*

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
        binding.switchButtonProfileLanguage.isChecked = resources.configuration.locale == Locale("en")
        binding.switchButtonProfileLanguage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) changeLanguage("en") else changeLanguage("vi")
        }

    }

    private fun changeLanguage(localeName: String) {
        val locale = Locale(localeName)
        resources.configuration.setLocale(locale)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
        val languageSharedPref = activity?.getSharedPreferences(Const.LANGUAGE_PREF, MODE_PRIVATE)
        languageSharedPref?.edit()?.putString(Const.LOCALE_CODE, localeName)?.apply()
        val refresh = Intent(
            this.requireContext(),
            HomeActivity::class.java
        )
        startActivity(refresh)
        activity?.finish()
    }
}