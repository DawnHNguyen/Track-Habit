package com.track.trackhabit.habit.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.track.trackhabit.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityFeature : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_feature)
    }
}