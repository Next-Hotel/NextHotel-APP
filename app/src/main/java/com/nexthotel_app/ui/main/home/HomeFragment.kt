package com.nexthotel_app.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.nexthotel_app.R
import com.nexthotel_app.databinding.FragmentHomeBinding
import com.nexthotel_app.ui.main.HotelPagingHorizontalAdapter
import com.nexthotel_app.ui.main.HotelPagingVerticalAdapter
import com.nexthotel_app.ui.main.LoadingStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    val adapterVertical = HotelPagingVerticalAdapter()
    val adapterHorizontal = HotelPagingHorizontalAdapter()

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
        initTextStatic()

        initSwipeToRefresh()

        setAdapterVertical()
        setAdapterHorizontal()

        viewModel.stateList.observe(viewLifecycleOwner, observerStateList)
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefreshVertical.setOnRefreshListener { viewModel.refresh() }
        binding.swipeRefreshHorizontal.setOnRefreshListener { viewModel.refresh() }
    }

    private fun initTextStatic() {
        binding.tvWelcome.text = getString(R.string.tv_welcome, "Username")
        binding.tvTitle.text = getString(R.string.tv_title)
        binding.tvBestForYou.text = getString(R.string.tv_bestForYou)
        binding.tvSpecialOffers.text = getString(R.string.tv_specialOffers)
    }

    private fun setAdapterVertical() {
        binding.itemVerticalRecyclerView.adapter = adapterVertical
        binding.itemVerticalRecyclerView.adapter = adapterVertical.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapterVertical.retry()
            }
        )

        lifecycleScope.launchWhenCreated {
            adapterVertical.loadStateFlow.collect {
                binding.swipeRefreshVertical.isRefreshing =
                    it.mediator?.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launch {
            adapterVertical.loadStateFlow.collectLatest { loadStates ->
                binding.viewError.root.isVisible = loadStates.refresh is LoadState.Error
            }
            if (adapterVertical.itemCount < 1) binding.viewError.root.visibility = View.VISIBLE
            else binding.viewError.root.visibility = View.GONE
        }
    }

    private fun setAdapterHorizontal() {
        binding.itemHorizontalRecyclerView.adapter = adapterHorizontal
        binding.itemHorizontalRecyclerView.adapter = adapterHorizontal.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapterHorizontal.retry()
            }
        )

        lifecycleScope.launchWhenCreated {
            adapterHorizontal.loadStateFlow.collect {
                binding.swipeRefreshHorizontal.isRefreshing =
                    it.mediator?.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launch {
            adapterHorizontal.loadStateFlow.collectLatest { loadStates ->
                binding.viewError.root.isVisible = loadStates.refresh is LoadState.Error
            }
            if (adapterHorizontal.itemCount < 1) binding.viewError.root.visibility = View.VISIBLE
            else binding.viewError.root.visibility = View.GONE
        }
    }

    private val observerStateList = Observer<HomeState> { itState ->
        binding.swipeRefreshVertical.isRefreshing = (itState == HomeState.OnLoading)
        when (itState) {
            is HomeState.OnSuccess -> {
                adapterVertical.submitData(lifecycle, itState.pagingHotel)
            }
            is HomeState.OnError -> {
                Toast.makeText(requireContext(), itState.message, Toast.LENGTH_LONG).show()
            }
            HomeState.OnLoading -> {}
        }
        binding.swipeRefreshHorizontal.isRefreshing = (itState == HomeState.OnLoading)
        when (itState) {
            is HomeState.OnSuccess -> {
                adapterHorizontal.submitData(lifecycle, itState.pagingHotel)
            }
            is HomeState.OnError -> {
                Toast.makeText(requireContext(), itState.message, Toast.LENGTH_LONG).show()
            }
            HomeState.OnLoading -> {}
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenCreated {
            setAdapterVertical()
            setAdapterHorizontal()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}