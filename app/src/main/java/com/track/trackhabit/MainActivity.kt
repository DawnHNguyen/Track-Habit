package com.track.trackhabit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.track.trackhabit.habit.presentation.ui.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("check_intent","da goi den")
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}