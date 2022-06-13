package com.nexthotel.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nexthotel.R
import com.nexthotel.core.data.local.entity.HotelEntity
import com.nexthotel.databinding.ItemHorizontalBinding

class HotelForYouAdapter(private val onBookmarkClick: (HotelEntity) -> Unit) :
    ListAdapter<HotelEntity, HotelForYouAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHorizontalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hotel = getItem(position)
        holder.bind(hotel)

        val bookmarkButton = holder.binding.bookmarkButton
//        bookmarkButton.apply {
//            if (hotel.isBookmarked) {
//                setImageDrawable(
//                    ContextCompat.getDrawable(context, R.drawable.ic_bookmark_blue)
//                )
//            } else {
//                setImageDrawable(
//                    ContextCompat.getDrawable(context, R.drawable.ic_bookmark_border_white)
//                )
//            }
//            setOnClickListener { onBookmarkClick(hotel) }
//        }
        if (hotel.isBookmarked) {
            bookmarkButton.setImageDrawable(
                ContextCompat.getDrawable(bookmarkButton.context, R.drawable.ic_bookmark_blue)
            )
        } else {
            bookmarkButton.setImageDrawable(
                ContextCompat.getDrawable(
                    bookmarkButton.context,
                    R.drawable.ic_bookmark_border_blue
                )
            )
        }
        bookmarkButton.setOnClickListener { onBookmarkClick(hotel) }
    }

    class MyViewHolder(val binding: ItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: HotelEntity) {
            binding.apply {
                imageView.load(hotel.imageUrl)
                nameTextView.text = hotel.name
                cityTextView.text = hotel.city
                rateTextView.text = hotel.rate
                priceTextView.text = hotel.priceRange

                itemView.setOnClickListener {
                    val destination = HomeFragmentDirections
                        .actionNavigationHomeToDetailFragment(hotel)
                    it.findNavController().navigate(destination)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<HotelEntity> =
            object : DiffUtil.ItemCallback<HotelEntity>() {
                override fun areItemsTheSame(old: HotelEntity, new: HotelEntity): Boolean {
                    return old.id == new.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(old: HotelEntity, new: HotelEntity): Boolean {
                    return old == new
                }
            }
    }
}