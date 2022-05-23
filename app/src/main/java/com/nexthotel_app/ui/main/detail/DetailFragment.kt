package com.nexthotel_app.ui.main.detail

import android.os.Bundle
import android.view.*
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

    private lateinit var menuDetail: Menu
    private val hotel = DetailFragmentArgs.fromBundle(arguments as Bundle).hotel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuDetail = menu
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_active -> {
                Toast.makeText(requireContext(), "Hotel unsaved", Toast.LENGTH_SHORT).show()
                item.isVisible = false
                menuDetail.findItem(R.id.favorite_nonactive).isVisible = true
                viewModel.delete(hotel.name)
                true
            }
            R.id.favorite_nonactive -> {
                Toast.makeText(requireContext(), "Hotel saved on Favorite Page", Toast.LENGTH_SHORT)
                    .show()
                item.isVisible = false
                menuDetail.findItem(R.id.favorite_active).isVisible = true
                viewModel.favorite(hotel)
                true
            }
            else -> true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

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

        with(hotel.name) {
            viewModel.find(this)
        }

        viewModel.favoriteState.observe(viewLifecycleOwner, favoriteObserver)
    }

    private val favoriteObserver = Observer<FavoriteState> {
        when (it) {
            FavoriteState.NotFound -> {
            }
            FavoriteState.OnSaved -> {
                menuDetail.findItem(R.id.favorite_active).isVisible = true
                menuDetail.findItem(R.id.favorite_nonactive).isVisible = false
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