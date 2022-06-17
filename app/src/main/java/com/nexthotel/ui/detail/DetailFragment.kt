package com.nexthotel.ui.detail

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import coil.load
import com.nexthotel.R
import com.nexthotel.core.data.remote.response.Hotel
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

        val hotel = DetailFragmentArgs.fromBundle(arguments as Bundle).hotel

        binding.apply {
            imageView.load(hotel.imageUrl)
            nameTextView.text = hotel.name
            cityTextView.text = hotel.city
            rateTextView.text = resources.getString(R.string.rateDetail, hotel.rate)
            reviewTextView.text = resources.getString(R.string.reviewsDetail, hotel.reviews)
            descTextView.text = hotel.description
            priceTextView.text = resources
                .getString(R.string.start_from) + " Rp. " + hotel.priceRange
            hotelStars.rating = hotel.stars.toFloat()

            facilityButton.setOnClickListener {
                val destination = DetailFragmentDirections
                    .actionDetailFragmentToFacilityFragment(hotel)
                it.findNavController().navigate(destination)
            }
            backButton.setOnClickListener { activity?.onBackPressed() }
            shareButton.setOnClickListener { share(hotel) }
        }
    }

    private fun share(hotel: Hotel) {
        val resolver = requireActivity().contentResolver
        val bitmapDrawable = binding.imageView.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val bitmapPath =
            MediaStore.Images.Media.insertImage(resolver, bitmap, "some title", "some desc")
        val bitmapUri = Uri.parse(bitmapPath)
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(
                Intent.EXTRA_SUBJECT,
                "SHARE FROM NEXT HOTEL APP"
            )
            this.putExtra(
                Intent.EXTRA_TEXT,
                """${hotel.name} that is in ${hotel.city} with a price range of Rp. ${hotel.priceRange}. This hotel have ${hotel.stars} stars and ${hotel.rate} rating from ${hotel.reviews} reviewers.
                            |Description :
                            |${hotel.description}""".trimMargin()
            )
            this.type = "image/*"
            this.putExtra(Intent.EXTRA_STREAM, bitmapUri)
        }
        startActivity(Intent.createChooser(shareIntent, "Share Via"))
    }
}