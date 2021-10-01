package com.track.trackhabit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.track.trackhabit.databinding.ActivityMainBinding
import com.track.trackhabit.presentation.ui.MainActivityFeature

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        Log.d("check_intent","da goi den")
        val intent = Intent(this, MainActivityFeature::class.java)
        startActivity(intent)

        setContentView(binding.root)

    }

}