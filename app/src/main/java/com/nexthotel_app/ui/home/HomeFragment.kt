package com.nexthotel_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nexthotel_app.databinding.FragmentHomeBinding
import com.nexthotel_app.ui.adapter.ListHotelHorizontalAdapter
import com.nexthotel_app.ui.adapter.ListHotelVerticalAdapter
import com.nexthotel_app.ui.viewmodel.HotelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HotelViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            hide()
        }

        getHotelsBestForYou()
        getHotelsSpecialOffers()
    }

    private fun getHotelsBestForYou() {
        val adapterVertical = ListHotelVerticalAdapter()
        binding.itemVerticalRecyclerView.adapter = adapterVertical
        viewModel.getHotelsBestForYou().observe(viewLifecycleOwner) { data ->
            adapterVertical.submitData(lifecycle, data)
        }
    }

    private fun getHotelsSpecialOffers() {
        val adapterHorizontal = ListHotelHorizontalAdapter()
        binding.itemHorizontalRecyclerView.adapter = adapterHorizontal
        viewModel.getHotelsSpecialOffers().observe(viewLifecycleOwner) { data ->
            adapterHorizontal.submitData(lifecycle, data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}