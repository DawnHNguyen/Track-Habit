package com.track.trackhabit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.track.trackhabit.databinding.ActivityMainBinding
import com.track.trackhabit.habit.presentation.ui.MainActivityFeature
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Log.d("check_intent","da goi den")
        val intent = Intent(this, MainActivityFeature::class.java)
        startActivity(intent)
    }
}