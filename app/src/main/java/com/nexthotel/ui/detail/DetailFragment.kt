package com.nexthotel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.nexthotel.R
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: DetailViewModel by viewModels { factory }

        val hotel = DetailFragmentArgs.fromBundle(arguments as Bundle).hotel
        val (_, name, city, imageUrl, rating, description, priceRange) = hotel

        binding.apply {
            imageView.load(imageUrl)
            nameTextView.text = name
            cityTextView.text = city
            rateTextView.text = rating
            descTextView.text = description
            priceTextView.text = priceRange

            backButton.setOnClickListener {
                activity?.onBackPressed()
            }
            shareButton.setOnClickListener {
                Toast.makeText(requireActivity(), "Share Button", Toast.LENGTH_SHORT).show()
            }
            moreButton.setOnClickListener {
                Toast.makeText(requireActivity(), "More Button", Toast.LENGTH_SHORT).show()
            }

            if (hotel.isBookmarked) {
                bookmarkButton.setImageDrawable(
                    ContextCompat.getDrawable(bookmarkButton.context, R.drawable.ic_bookmark)
                )
            } else {
                bookmarkButton.setImageDrawable(
                    ContextCompat.getDrawable(bookmarkButton.context, R.drawable.ic_bookmark_border)
                )
            }
            bookmarkButton.setOnClickListener {
                if (hotel.isBookmarked) viewModel.deleteHotel(hotel) else viewModel.saveHotel(
                    hotel
                )
                if (hotel.isBookmarked) {
                    bookmarkButton.setImageDrawable(
                        ContextCompat.getDrawable(bookmarkButton.context, R.drawable.ic_bookmark)
                    )
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.bookmrak_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    bookmarkButton.setImageDrawable(
                        ContextCompat.getDrawable(
                            bookmarkButton.context,
                            R.drawable.ic_bookmark_border
                        )
                    )
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.unbookmark_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }

}