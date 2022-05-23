package com.nexthotel_app.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nexthotel_app.R
import com.nexthotel_app.databinding.FragmentFavoriteBinding
import com.nexthotel_app.ui.main.HotelAdapter
import com.nexthotel_app.utils.viewBinding
import org.koin.android.ext.android.inject


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)

    private val viewModel: FavoriteViewModel by inject()

    private val adapter = HotelAdapter {
        findNavController().navigate(
            FavoriteFragmentDirections.actionExploreFragmentToDetailFragment(
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

        binding.rvFavorite.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ListFavoriteState.OnSuccess -> {
                    adapter.setList(it.list)
                }
                is ListFavoriteState.OnError -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                ListFavoriteState.OnLoading -> {

                }
            }
        }

        binding.fabDelete.setOnClickListener {
            viewModel.deleteAll()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.list()
    }

}