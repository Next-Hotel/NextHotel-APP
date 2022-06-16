package com.nexthotel.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nexthotel.R
import com.nexthotel.core.data.Result
import com.nexthotel.core.ui.BestPickAdapter
import com.nexthotel.core.ui.HotelForYouAdapter
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.core.utils.Utils.toast
import com.nexthotel.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        viewModel.getHotelForYou().observe(viewLifecycleOwner) {
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