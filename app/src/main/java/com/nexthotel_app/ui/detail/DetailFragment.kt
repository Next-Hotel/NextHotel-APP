package com.nexthotel_app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.load
import com.nexthotel_app.R
import com.nexthotel_app.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            ivHotel.load(hotel.imageUrl) {
                placeholder(R.drawable.ic_baseline_place_holder_24)
            }
            tvHotelName.text = hotel.namaHotel
            tvHotelRating.text = hotel.rating
            tvHotelCity.text = hotel.kota
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