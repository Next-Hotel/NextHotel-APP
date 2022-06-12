package com.nexthotel.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.databinding.FragmentBookmarksBinding

class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: BookmarkViewModel by viewModels { factory }

        val hotelAdapter = BookmarkAdapter {
            if (it.isBookmarked) viewModel.deleteHotel(it) else viewModel.saveHotel(it)
        }

        viewModel.getBookmarkedHotel().observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            if (it.isEmpty()) {
                binding.otherResult.visibility = View.VISIBLE
            } else {
                binding.otherResult.visibility = View.GONE
            }
            hotelAdapter.submitList(it)
        }

        binding.bookmarkRecyclerView.adapter = hotelAdapter
    }
}