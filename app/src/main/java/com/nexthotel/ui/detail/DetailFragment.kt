package com.nexthotel.ui.detail

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.insertImage
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import com.nexthotel.R
import com.nexthotel.core.data.local.entity.HotelEntity
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.core.utils.Utils.toast
import com.nexthotel.databinding.FragmentDetailBinding
import com.nexthotel.ui.bookmarks.BookmarksFragmentDirections

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

        binding.apply {
            imageView.load(hotel.imageUrl)
            nameTextView.text = hotel.name
            cityTextView.text = hotel.city
            rateTextView.text = resources.getString(R.string.rateDetail, hotel.rate)
            reviewTextView.text = resources.getString(R.string.reviewsDetail, hotel.reviews)
            descTextView.text = hotel.description
            priceTextView.text = hotel.priceRange
            hotelStars.numStars = hotel.stars.toInt()

            facilityButton.setOnClickListener {
                val destination = DetailFragmentDirections
                    .actionDetailFragmentToFacilityFragment(hotel)
                it.findNavController().navigate(destination)
            }
            backButton.setOnClickListener { activity?.onBackPressed() }
            shareButton.setOnClickListener { share(hotel) }
            bookmarkButton.apply {
                if (hotel.isBookmarked) setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.ic_bookmark_white)
                ) else setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.ic_bookmark_border_white)
                )
                setOnClickListener {
                    if (hotel.isBookmarked) {
                        viewModel.deleteHotel(hotel)
                    } else {
                        viewModel.saveHotel(hotel)
                    }
                    if (hotel.isBookmarked) {
                        setImageDrawable(
                            ContextCompat.getDrawable(context, R.drawable.ic_bookmark_white)
                        )
                        toast(requireActivity(), getString(R.string.bookmark_toast))
                    } else {
                        setImageDrawable(
                            ContextCompat.getDrawable(context, R.drawable.ic_bookmark_border_white)
                        )
                        toast(requireActivity(), getString(R.string.unbookmarked_toast))
                    }
                }
            }
        }
    }

    private fun share(hotel: HotelEntity) {
        val resolver = requireActivity().contentResolver
        val bitmapDrawable = binding.imageView.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val bitmapPath = insertImage(resolver, bitmap, "some title", "some desc")
        val bitmapUri = Uri.parse(bitmapPath)
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(
                Intent.EXTRA_SUBJECT,
                "SHARE FROM NEXT HOTEL APP"
            )
            this.putExtra(
                Intent.EXTRA_TEXT,
                """${hotel.name} that is in ${hotel.city} with a price range of ${hotel.priceRange} and this hotel have ${hotel.rate} ⭐
                            |Description :
                            |${hotel.description}""".trimMargin()
            )
            this.type = "image/*"
            this.putExtra(Intent.EXTRA_STREAM, bitmapUri)
        }
        startActivity(Intent.createChooser(shareIntent, "Share Via"))
    }
}