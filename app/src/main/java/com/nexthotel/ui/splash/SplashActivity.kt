package com.nexthotel.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.nexthotel.R
import com.nexthotel.ui.MainActivity
import com.nexthotel.ui.survey.SurveyActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SurveyActivity::class.java))
            finish()
        }, 2000L)
    }
}