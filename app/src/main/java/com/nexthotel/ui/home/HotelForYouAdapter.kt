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
        val binding =
            ItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hotel = getItem(position)
        holder.bind(hotel)

        val bookmarkButton = holder.binding.bookmarkButton
        if (hotel.isBookmarked) {
            bookmarkButton.setImageDrawable(
                ContextCompat.getDrawable(bookmarkButton.context, R.drawable.ic_bookmark)
            )
        } else {
            bookmarkButton.setImageDrawable(
                ContextCompat.getDrawable(bookmarkButton.context, R.drawable.ic_bookmark_border)
            )
        }
        bookmarkButton.setOnClickListener { onBookmarkClick(hotel) }
    }

    class MyViewHolder(val binding: ItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: HotelEntity) {
            val (_, name, city, imageUrl, rate, _, priceRange) = hotel
            binding.apply {
                imageView.load(imageUrl)
                nameTextView.text = name
                cityTextView.text = city
                rateTextView.text = rate
                priceTextView.text = priceRange

                itemView.setOnClickListener {
                    val toDetail =
                        HomeFragmentDirections.actionNavigationHomeToDetailFragment(hotel)
                    it.findNavController().navigate(toDetail)
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