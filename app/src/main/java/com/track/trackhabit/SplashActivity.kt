package com.track.trackhabit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import com.track.common.base.constpackage.HawkKey
import com.track.trackhabit.auth.presentation.ui.AuthenticationActivity
import com.track.trackhabit.habit.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(600)
            val intent = if (Hawk.contains(HawkKey.ACCESS_TOKEN) || Hawk.get<Boolean>(HawkKey.IS_USE_ACC) == false) Intent(this@SplashActivity, MainActivity::class.java)
            else Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}