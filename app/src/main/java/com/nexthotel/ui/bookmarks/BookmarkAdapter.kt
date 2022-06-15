package com.nexthotel.ui.bookmarks

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

    class MyViewHolder(val binding: ItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: HotelEntity) {
            binding.apply {
                val idrPrice = "IDR " + hotel.priceRange
                imageView.load(hotel.imageUrl){
                    error(R.drawable.ic_error_hotel)
                }
                nameTextView.text = hotel.name
                cityTextView.text = hotel.city
                rateTextView.text = hotel.rate
                ratingBar1.rating = hotel.stars.toFloat()
                descTextView.text = hotel.description
                priceTextView.text = idrPrice

                itemView.setOnClickListener {
                    val detail = BookmarksFragmentDirections
                        .actionNavigationBookmarksToDetailFragment(hotel)
                    it.findNavController().navigate(detail)
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