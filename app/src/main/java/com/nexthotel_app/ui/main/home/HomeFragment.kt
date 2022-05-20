package com.nexthotel_app.ui.main.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nexthotel_app.R
import com.nexthotel_app.databinding.FragmentHomeBinding
import com.nexthotel_app.ui.main.HotelPagingVerticalAdapter
import com.nexthotel_app.ui.main.LoadingStateAdapter
import com.nexthotel_app.utils.viewBinding
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by inject()
    private val adapter = HotelPagingVerticalAdapter {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            ActivityCompat.finishAffinity(requireActivity())
        }
        callback.isEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.apply {
            hide()
        }
//        setAdapterVertical()
//        setAdapterHorizontal()
        setAdapter()

        binding.swipeRefreshVertical.setOnRefreshListener { viewModel.refresh() }
//        binding.swipeRefreshHorizontal.setOnRefreshListener { viewModel.refresh() }

        viewModel.stateList.observe(viewLifecycleOwner, observerStateList)
    }

    private fun setAdapter() {
        binding.itemVerticalRecyclerView.adapter = adapter
        binding.itemVerticalRecyclerView.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
    }


    private val observerStateList = Observer<HomeState> { itState ->
        binding.swipeRefreshVertical.isRefreshing = (itState == HomeState.OnLoading)
        when (itState) {
            is HomeState.OnSuccess -> {
                adapter.submitData(lifecycle, itState.pagingHotel)
            }
            is HomeState.OnError -> {
                Toast.makeText(requireContext(), itState.message, Toast.LENGTH_LONG).show()
            }
            HomeState.OnLoading -> {}
        }
    }

//    private fun setAdapterVertical() {
//        binding.itemVerticalRecyclerView.adapter = adapterVertical
//        binding.itemVerticalRecyclerView.adapter = adapterVertical.withLoadStateFooter(
//            footer = LoadingStateAdapter {
//                adapterVertical.retry()
//            }
//        )
//    }
//
//    private fun setAdapterHorizontal() {
//        binding.itemHorizontalRecyclerView.adapter = adapterHorizontal
//        binding.itemHorizontalRecyclerView.adapter = adapterHorizontal.withLoadStateFooter(
//            footer = LoadingStateAdapter {
//                adapterHorizontal.retry()
//            }
//        )
//    }

//    private val observerStateList = Observer<HomeState> { itState ->
//        binding.swipeRefreshVertical.isRefreshing = (itState == HomeState.OnLoading)
//        when (itState) {
//            is HomeState.OnSuccess -> {
//                adapterVertical.submitData(lifecycle, itState.pagingHotel)
//            }
//            is HomeState.OnError -> {
//                Toast.makeText(requireContext(), itState.message, Toast.LENGTH_LONG).show()
//            }
//            HomeState.OnLoading -> {}
//        }
//        binding.swipeRefreshHorizontal.isRefreshing = (itState == HomeState.OnLoading)
//        when (itState) {
//            is HomeState.OnSuccess -> {
//                adapterHorizontal.submitData(lifecycle, itState.pagingHotel)
//            }
//            is HomeState.OnError -> {
//                Toast.makeText(requireContext(), itState.message, Toast.LENGTH_LONG).show()
//            }
//            HomeState.OnLoading -> {}
//        }
//    }


}