package com.nexthotel.ui.survey

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
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

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: SurveyViewModel by viewModels { factory }
        val adapter = SurveyAdapter { viewModel.setSelectedSurvey(it) }
        pref = DataStoreSurvey.getInstance(dataStore)

        viewModel.getListSurvey().observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {}
                    is Result.Success -> {
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
            lifecycleScope.launch {
                pref.setIsSurvey(IsSurvey.TRUE)
                pref.setRecommendation(viewModel.getSelectedSurvey())

                showLoading(true)
                startActivity(Intent(this@SurveyActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        when {
            isLoading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.submit.visibility = View.GONE
            }
            else -> binding.progressBar.visibility = View.GONE
        }
    }
}