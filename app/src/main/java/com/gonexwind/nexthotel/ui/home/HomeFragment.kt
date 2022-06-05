package com.gonexwind.nexthotel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.gonexwind.nexthotel.adapter.HotelHorizontalAdapter
import com.gonexwind.nexthotel.adapter.HotelVerticalAdapter
import com.gonexwind.nexthotel.databinding.FragmentHomeBinding
import com.gonexwind.nexthotel.model.Hotel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<HomeViewModel>()

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

        viewModel.listHotel.observe(requireActivity()) { setHotelData(it) }
        viewModel.isLoading.observe(requireActivity()) { showLoading(it) }
    }

    private fun setHotelData(listHotel: List<Hotel>) {
        val verticalAdapter = HotelVerticalAdapter(listHotel)
        val horizontalAdapter = HotelHorizontalAdapter(listHotel)

        binding.apply {
            verticalRecyclerView.adapter = verticalAdapter
            horizontalRecyclerView.adapter = horizontalAdapter
        }

        verticalAdapter.setOnItemClickCallback(object :
            HotelVerticalAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hotel) {
                val toDetail =
                    HomeFragmentDirections.actionNavigationHomeToDetailFragment(data)
                view?.findNavController()?.navigate(toDetail)
            }
        })

        horizontalAdapter.setOnItemClickCallback(object :
            HotelHorizontalAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hotel) {
                val toDetail =
                    HomeFragmentDirections.actionNavigationHomeToDetailFragment(data)
                view?.findNavController()?.navigate(toDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        when {
            isLoading -> binding.progressBar.visibility = View.VISIBLE
            else -> binding.progressBar.visibility = View.GONE
        }
    }

}