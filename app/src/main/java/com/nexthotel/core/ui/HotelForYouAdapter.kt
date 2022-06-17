package com.nexthotel.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nexthotel.core.data.remote.response.Hotel
import com.nexthotel.databinding.ItemHorizontalBinding
import com.nexthotel.ui.home.HomeFragmentDirections

class HotelForYouAdapter(private val onBookmarkClick: (Hotel) -> Unit) :
    ListAdapter<Hotel, HotelForYouAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHorizontalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hotel = getItem(position)
        holder.bind(hotel)
    }

    class MyViewHolder(private val binding: ItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: Hotel) {
            binding.apply {
                imageView.load(hotel.imageUrl)
                nameTextView.text = hotel.name
                cityTextView.text = hotel.city
                rateTextView.text = hotel.rate.toString() + " / 10"
                priceTextView.text = "Rp. " + hotel.priceRange

                itemView.setOnClickListener {
                    val destination = HomeFragmentDirections
                        .actionNavigationHomeToDetailFragment(hotel)
                    it.findNavController().navigate(destination)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Hotel> =
            object : DiffUtil.ItemCallback<Hotel>() {
                override fun areItemsTheSame(old: Hotel, new: Hotel): Boolean {
                    return old.id == new.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(old: Hotel, new: Hotel): Boolean {
                    return old == new
                }
            }
    }
}