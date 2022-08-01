package com.track.trackhabit.habit.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.track.trackhabit.habit.R
import com.track.trackhabit.habit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val homeActivityViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = homeActivityViewModel.getLanguagePref()
        val locale = Locale(language)
        resources.configuration.setLocale(locale)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)

        binding = ActivityMainBinding.inflate(layoutInflater)
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