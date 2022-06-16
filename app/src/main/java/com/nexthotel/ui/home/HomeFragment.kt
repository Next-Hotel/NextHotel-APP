package com.nexthotel.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.nexthotel.R
import com.nexthotel.core.data.Result
import com.nexthotel.core.data.local.datastore.DataStoreSurvey
import com.nexthotel.core.ui.BestPickAdapter
import com.nexthotel.core.ui.HotelForYouAdapter
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.core.utils.Utils.toast
import com.nexthotel.databinding.FragmentHomeBinding

private val Context.dataStore by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: DataStoreSurvey


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels { factory }
        pref = DataStoreSurvey.getInstance(requireContext().dataStore)

        val bestPickAdapter = BestPickAdapter {
            if (it.isBookmarked) {
                viewModel.deleteHotel(it)
                toast(requireActivity(), getString(R.string.unbookmarked_toast))
            } else {
                viewModel.saveHotel(it)
                toast(requireActivity(), getString(R.string.bookmark_toast))
            }
        }

        val hotelForYouAdapter = HotelForYouAdapter {
            if (it.isBookmarked) {
                viewModel.deleteHotel(it)
                toast(requireActivity(), getString(R.string.unbookmarked_toast))
            } else {
                viewModel.saveHotel(it)
                toast(requireActivity(), getString(R.string.bookmark_toast))
            }
        }



        viewModel.getBestPick().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        bestPickAdapter.submitList(it.data)
                    }
                    is Result.Error -> {
                        showLoading(true)
                        toast(requireActivity(), getString(R.string.check_internet))
                        Log.d("ALHAMDULILLAH", it.error)
                    }
                }
            }
        }

        pref.recommendationFlow.asLiveData().observe(viewLifecycleOwner) {
            viewModel.getHotelForYou(it).observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            hotelForYouAdapter.submitList(it.data)
                        }
                        is Result.Error -> {
                            showLoading(true)
                            toast(requireActivity(), getString(R.string.check_internet))
                        }
                    }
                }
            }
        }

        binding.bestPickRecyclerView.adapter = bestPickAdapter
        binding.hotelForYouRecyclerView.adapter = hotelForYouAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        when {
            isLoading -> binding.progressBar.visibility = View.VISIBLE
            else -> binding.progressBar.visibility = View.GONE
        }
    }
}