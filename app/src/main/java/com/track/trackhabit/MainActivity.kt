package com.track.trackhabit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.track.trackhabit.presentation.ui.MainActivityFeature

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivityFeature::class.java)
        startActivity(intent)
        }
    }
}