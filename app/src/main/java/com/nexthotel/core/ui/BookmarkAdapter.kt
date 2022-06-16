package com.nexthotel.core.ui

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
import com.nexthotel.databinding.ItemVerticalBinding
import com.nexthotel.ui.bookmarks.BookmarksFragmentDirections

class BookmarkAdapter(private val onBookmarkClick: (HotelEntity) -> Unit) :
    ListAdapter<HotelEntity, BookmarkAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemVerticalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hotel = getItem(position)
        holder.bind(hotel)

        val bookmarkButton = holder.binding.bookmarkButton
        bookmarkButton.apply {
            if (hotel.isBookmarked) setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_bookmark_white)
            ) else setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_bookmark_border_white)
            )
            setOnClickListener { onBookmarkClick(hotel) }
        }
    }

    class MyViewHolder(val binding: ItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: HotelEntity) {
            binding.apply {
                imageView.load(hotel.imageUrl)
                nameTextView.text = hotel.name
                cityTextView.text = hotel.city
                rateTextView.text = hotel.rate
                descTextView.text = hotel.description
                priceTextView.text = hotel.priceRange

                itemView.setOnClickListener {
                    val destination = BookmarksFragmentDirections
                        .actionNavigationBookmarksToDetailFragment(hotel)
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