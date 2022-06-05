package com.nexthotel_app.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.nexthotel_app.R
import com.nexthotel_app.databinding.FragmentDetailBinding
import org.koin.android.ext.android.inject

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by inject()
    private val hotel by lazy {
        DetailFragmentArgs.fromBundle(requireArguments()).hotel
    }

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

        with(hotel.id) {
            viewModel.find(this)
        }
        setupUI()
        setupAction()
    }

    private fun setupAction() {
        binding.fabAdd.setOnClickListener {
            if (binding.fabAdd.tag == R.drawable.ic_baseline_favorite_24) {
                viewModel.delete(hotel.id)
                binding.fabAdd.setImageResource(R.drawable.ic_favorite_border_24)
            } else {
                viewModel.favorite(hotel)
                binding.fabAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
        }
        viewModel.state.observe(viewLifecycleOwner, favoriteObserver)
    }

    private fun setupUI() {
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

    private val favoriteObserver = Observer<FavoriteState> {
        when (it) {
            FavoriteState.NotFound -> {
            }
            FavoriteState.OnSaved -> {
                binding.fabAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
//                menuDetail.findItem(R.id.favorite_active).isVisible = true
//                menuDetail.findItem(R.id.favorite_nonactive).isVisible = false
            }
            is FavoriteState.OnError -> {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}