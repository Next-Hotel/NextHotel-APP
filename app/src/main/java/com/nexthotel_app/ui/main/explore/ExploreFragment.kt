package com.nexthotel_app.ui.main.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nexthotel_app.R
import com.nexthotel_app.databinding.FragmentExploreBinding
import com.nexthotel_app.ui.main.HotelAdapter
import com.nexthotel_app.utils.viewBinding
import org.koin.android.ext.android.inject

class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private val binding by viewBinding(FragmentExploreBinding::bind)
    private val viewModel: ExploreViewModel by inject()
    private val adapter = HotelAdapter {
        findNavController().navigate(
            ExploreFragmentDirections.actionExploreFragmentToDetailFragment(
                it
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swiperefresh.setOnRefreshListener { viewModel.refresh() }
        binding.itemVerticalRecyclerView.adapter = adapter
        viewModel.stateList.observe(viewLifecycleOwner, observerStateList)
        binding.searchview.setOnQueryTextListener(onQueryTextListener)
    }

    private val observerStateList = Observer<ExploreState> {
        binding.swiperefresh.isRefreshing = (it == ExploreState.OnLoading)
        when (it) {
            is ExploreState.OnSuccess -> {
                adapter.setList(it.list)
            }
            is ExploreState.OnError -> {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
            ExploreState.OnLoading -> {}
        }
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            viewModel.search(query ?: "")
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            viewModel.search(newText ?: "")
            return false
        }
    }
}