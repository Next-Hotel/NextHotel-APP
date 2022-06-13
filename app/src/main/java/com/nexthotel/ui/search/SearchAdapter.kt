package com.nexthotel.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nexthotel.R
import com.nexthotel.core.data.local.entity.HotelEntity
import com.nexthotel.databinding.ItemVerticalBinding

class SearchAdapter(private val onBookmarkClick: (HotelEntity) -> Unit) :
    ListAdapter<HotelEntity, SearchAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: HotelEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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

    inner class MyViewHolder(val binding: ItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: HotelEntity) {
            val (_, name, city, imageUrl, rate, description, priceRange) = hotel
            binding.apply {
                imageView.load(imageUrl)
                nameTextView.text = name
                cityTextView.text = city
                rateTextView.text = StringBuilder(rate).append(" ⭐")
                descTextView.text = description
                priceTextView.text = priceRange
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(hotel)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<HotelEntity> =
            object : DiffUtil.ItemCallback<HotelEntity>() {
                override fun areItemsTheSame(oldUser: HotelEntity, newUser: HotelEntity): Boolean {
                    return oldUser.id == newUser.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldUser: HotelEntity,
                    newUser: HotelEntity
                ): Boolean {
                    return oldUser == newUser
                }
            }
    }
}