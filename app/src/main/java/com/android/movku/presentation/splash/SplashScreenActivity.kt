package com.android.movku.presentation.splash

import android.content.Intent
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.movku.R
import com.android.movku.presentation.auth.login.LoginActivity
import com.android.movku.presentation.movie.popular.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed(3000) {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}