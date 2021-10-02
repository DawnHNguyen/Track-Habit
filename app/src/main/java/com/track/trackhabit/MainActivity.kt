package com.track.trackhabit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.track.trackhabit.presentation.ui.MainActivityFeature

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val button: Button = findViewById(R.id.button_activityMainApp_intent)
        button.setOnClickListener {
        val intent = Intent(this, MainActivityFeature::class.java)
        startActivity(intent)
        }
    }
}