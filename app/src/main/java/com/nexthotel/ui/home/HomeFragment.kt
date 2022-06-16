package com.nexthotel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nexthotel.R
import com.nexthotel.core.data.Result
import com.nexthotel.core.ui.ViewModelFactory
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
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.unbookmarked_toast),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                viewModel.saveHotel(it)
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.bookmark_toast),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val hotelForYouAdapter = HotelForYouAdapter {
            if (it.isBookmarked) {
                viewModel.deleteHotel(it)
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.unbookmarked_toast),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                viewModel.saveHotel(it)
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.bookmark_toast),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.getBestPick().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        val hotelData = it.data
                        bestPickAdapter.submitList(hotelData)
                    }
                    is Result.Error -> {
                        showLoading(true)
                        Toast.makeText(
                            context,
                            "Please Check Your Internet",
                            Toast.LENGTH_SHORT
                        ).show()
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
                        val hotelData = it.data
                        hotelForYouAdapter.submitList(hotelData)
                    }
                    is Result.Error -> {
                        showLoading(true)
                        Toast.makeText(
                            context,
                            "Please Check Your Internet",
                            Toast.LENGTH_SHORT
                        ).show()
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