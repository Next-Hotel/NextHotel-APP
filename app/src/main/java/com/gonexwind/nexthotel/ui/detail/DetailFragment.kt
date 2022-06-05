package com.gonexwind.nexthotel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import com.gonexwind.nexthotel.databinding.FragmentDetailBinding

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
            bookmarkButton.setOnClickListener {
                Toast.makeText(requireActivity(), "Bookmark Button", Toast.LENGTH_SHORT).show()
            }
            moreButton.setOnClickListener {
                Toast.makeText(requireActivity(), "More Button", Toast.LENGTH_SHORT).show()
            }
        }
    }

}