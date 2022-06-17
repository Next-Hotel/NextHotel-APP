package com.nexthotel.ui.survey

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.nexthotel.R
import com.nexthotel.core.data.Result
import com.nexthotel.core.data.local.datastore.DataStoreSurvey
import com.nexthotel.core.data.local.datastore.IsSurvey
import com.nexthotel.core.ui.SurveyAdapter
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.core.utils.Utils
import com.nexthotel.databinding.ActivitySurveyBinding
import com.nexthotel.ui.MainActivity
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySurveyBinding
    private lateinit var pref: DataStoreSurvey


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = DataStoreSurvey.getInstance(dataStore)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: SurveyViewModel by viewModels { factory }
        val adapter = SurveyAdapter {
            viewModel.setSelectedSurvey(it)
        }

        viewModel.getListSurvey().observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        Log.e("Thor", it.data.toList().toString())
                        adapter.setList(it.data.toList())
                        binding.surveyRecyclerView.adapter = adapter
                    }
                    is Result.Error -> {
                        Utils.toast(this, getString(R.string.check_internet))
                    }
                }
            }
        }

        binding.submit.setOnClickListener {
            // change survey state
            lifecycleScope.launch {
                pref.setRecommendation(viewModel.getSelectedSurvey())
                pref.setIsSurvey(IsSurvey.TRUE)
            }
        }

        pref.modeUIFlow.asLiveData().observe(this) {
            if (it == IsSurvey.TRUE) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}