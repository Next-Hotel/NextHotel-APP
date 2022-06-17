package com.nexthotel.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.nexthotel.R
import com.nexthotel.core.data.local.datastore.DataStoreSurvey
import com.nexthotel.core.data.local.datastore.IsSurvey
import com.nexthotel.ui.MainActivity
import com.nexthotel.ui.survey.SurveyActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashActivity : AppCompatActivity() {

    private lateinit var pref: DataStoreSurvey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        pref = DataStoreSurvey.getInstance(dataStore)

        setSurveyView()
    }

    private fun setSurveyView() {
        pref.modeUIFlow.asLiveData().observe(this) {
            if (it == IsSurvey.TRUE) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 2000L)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, SurveyActivity::class.java))
                    finish()
                }, 2000L)
            }
        }
    }
}