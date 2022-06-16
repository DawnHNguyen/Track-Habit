package com.track.trackhabit.habit.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.data.local.SharedPrefs
import com.track.trackhabit.habit.databinding.ActivityHomeBinding
import com.track.trackhabit.habit.presentation.constpackage.Const
import com.track.trackhabit.habit.presentation.constpackage.ConstLanguageCode
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = SharedPrefs().getStringSharedPreferences(
            this,
            Const.LANGUAGE_PREF,
            Const.LANGUAGE_CODE,
            ConstLanguageCode.US
        )
        val locale = Locale(language)
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