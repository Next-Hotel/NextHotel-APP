package com.nexthotel.ui.survey

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nexthotel.core.data.local.datastore.DataStoreSurvey
import com.nexthotel.core.data.local.datastore.IsSurvey
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.databinding.FragmentSurveyBinding
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SurveyFragment : Fragment() {

    private var _binding: FragmentSurveyBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: DataStoreSurvey
    private val adapter = SurveyAdapter {
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurveyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: SurveyViewModel by viewModels { factory }

        pref = DataStoreSurvey.getInstance(requireContext().dataStore)

        adapter.setList(viewModel.result.value ?: listOf(""))
        binding.surveyRecyclerView.adapter = adapter

        binding.submit.setOnClickListener {
            lifecycleScope.launch {
                pref.setIsSurvey(IsSurvey.TRUE)
            }
        }
    }

}