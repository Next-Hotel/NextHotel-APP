package com.nexthotel.ui.survey

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nexthotel.R
import com.nexthotel.core.data.Result
import com.nexthotel.core.data.local.datastore.DataStoreSurvey
import com.nexthotel.core.data.local.datastore.IsSurvey
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.core.utils.Utils
import com.nexthotel.databinding.FragmentSurveyBinding
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SurveyFragment : Fragment() {

    private var _binding: FragmentSurveyBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: DataStoreSurvey

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
        val adapter = SurveyAdapter {
            viewModel.setSelectedSurvey(it)
        }

        pref = DataStoreSurvey.getInstance(requireContext().dataStore)

        viewModel.getListSurvey().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        Log.e("Thor", it.data.toList().toString())
                        adapter.setList(it.data.toList())
                        binding.surveyRecyclerView.adapter = adapter
                    }
                    is Result.Error -> {
                        Utils.toast(requireActivity(), getString(R.string.check_internet))
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
    }

}