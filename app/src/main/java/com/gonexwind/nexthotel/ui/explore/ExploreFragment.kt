package com.gonexwind.nexthotel.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.gonexwind.nexthotel.adapter.ExploreAdapter
import com.gonexwind.nexthotel.databinding.FragmentExploreBinding
import com.gonexwind.nexthotel.model.Hotel

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ExploreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
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
        val adapter = ExploreAdapter(listHotel)
        binding.verticalRecyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object :
            ExploreAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hotel) {
                val toDetail =
                    ExploreFragmentDirections.actionNavigationExploreToDetailFragment(data)
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