package com.nexthotel.ui.detail

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
        val (_, name, city, imageUrl, rate, description, priceRange) = hotel

        binding.apply {
            imageView.load(imageUrl)
            nameTextView.text = name
            cityTextView.text = city
            rateTextView.text = StringBuilder(rate).append(" ⭐")
            descTextView.text = description
            priceTextView.text = priceRange

            backButton.setOnClickListener {
                activity?.onBackPressed()
            }
            shareButton.setOnClickListener {
                val resolver = requireActivity().contentResolver
                val bitmapDrawable = imageView.drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap
                val bitmapPath =
                    MediaStore.Images.Media.insertImage(
                        resolver,
                        bitmap,
                        "some title",
                        "some description"
                    )
                val bitmapUri = Uri.parse(bitmapPath)
                val shareIntent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(
                        Intent.EXTRA_SUBJECT,
                        "SHARE FROM NEXT HOTEL APP"
                    )
                    this.putExtra(
                        Intent.EXTRA_TEXT,
                        """$name that is in $city with a price range of $priceRange and this hotel have $rate ⭐
                            |Description :
                            |$description""".trimMargin()
                    )
                    this.type = "image/*"
                    this.putExtra(
                        Intent.EXTRA_STREAM,
                        bitmapUri
                    )
                }
                startActivity(Intent.createChooser(shareIntent, "Share Via"))
            }
            moreButton.setOnClickListener {
                Toast.makeText(requireActivity(), "More Button", Toast.LENGTH_SHORT).show()
            }

            if (hotel.isBookmarked) {
                bookmarkButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        bookmarkButton.context,
                        R.drawable.ic_bookmark_white
                    )
                )
            } else {
                bookmarkButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        bookmarkButton.context,
                        R.drawable.ic_bookmark_border_white
                    )
                )
            }
            bookmarkButton.setOnClickListener {
                if (hotel.isBookmarked) viewModel.deleteHotel(hotel) else viewModel.saveHotel(
                    hotel
                )
                if (hotel.isBookmarked) {
                    bookmarkButton.setImageDrawable(
                        ContextCompat.getDrawable(
                            bookmarkButton.context,
                            R.drawable.ic_bookmark_white
                        )
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
                            R.drawable.ic_bookmark_border_white
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