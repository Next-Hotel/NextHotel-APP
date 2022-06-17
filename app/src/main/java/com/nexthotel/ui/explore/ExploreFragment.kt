package com.nexthotel.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nexthotel.R
import com.nexthotel.core.data.Result
import com.nexthotel.core.ui.ExploreAdapter
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.core.utils.Utils
import com.nexthotel.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

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

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: ExploreViewModel by viewModels { factory }


        val exploreAdapter = ExploreAdapter {}

        viewModel.getExplore().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        exploreAdapter.submitList(it.data)
                    }
                    is Result.Error -> {
                        showLoading(true)
                        Utils.toast(requireActivity(), getString(R.string.check_internet))
                    }
                }
            }
        }

        binding.verticalRecyclerView.adapter = exploreAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        when {
            isLoading -> binding.progressBar.visibility = View.VISIBLE
            else -> binding.progressBar.visibility = View.GONE
        }
    }
}