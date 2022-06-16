package com.track.trackhabit.habit.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.ActivityHomeBinding
import com.track.trackhabit.habit.presentation.constpackage.Const
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val languageSharedPref = getSharedPreferences(Const.LANGUAGE_PREF, MODE_PRIVATE)
        val language = languageSharedPref.getString(Const.LOCALE_CODE, "en")
        val locale = Locale(language!!)
        resources.configuration.setLocale(locale)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBottom: BottomNavigationView = binding.bottomNavActivityHomeBottomNav
        val navController = findNavController(R.id.nav_host_fragment)

        AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_statistical, R.id.nav_sleeptime, R.id.nav_profile
            )
        )

        navBottom.setupWithNavController(navController)
    }

}