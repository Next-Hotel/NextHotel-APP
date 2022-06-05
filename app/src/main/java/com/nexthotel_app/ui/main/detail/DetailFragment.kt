package com.nexthotel_app.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nexthotel_app.R
import com.nexthotel_app.databinding.FragmentDetailBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        val hotel = DetailFragmentArgs.fromBundle(arguments as Bundle).hotel

        binding.apply {
            Glide.with(binding.root.context)
                .load(hotel.imageUrl) // URL Gambar
                .placeholder(R.drawable.ic_baseline_place_holder_24) // placeholder
                .error(R.drawable.ic_baseline_broken_image_24) // while error
                .fallback(R.drawable.ic_baseline_place_holder_24) // while null
                .into(binding.ivHotel) // imageView mana yang akan diterapkan

            tvHotelName.text = hotel.name
            tvHotelRating.text = hotel.rating
            tvHotelCity.text = hotel.city
            tvHotelPriceRange.text = hotel.priceRange
            tvHotelDesc.text = hotel.description

            ivBackArrow.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}