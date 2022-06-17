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
import com.nexthotel.databinding.ItemVerticalBinding
import com.nexthotel.ui.explore.ExploreFragmentDirections

class ExploreAdapter(private val onBookmarkClick: (Hotel) -> Unit) :
    ListAdapter<Hotel, ExploreAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemVerticalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hotel = getItem(position)
        holder.bind(hotel)
    }

    class MyViewHolder(private val binding: ItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: Hotel) {
            binding.apply {
                imageView.load(hotel.imageUrl)
                nameTextView.text = hotel.name
                cityTextView.text = hotel.city
                descTextView.text = hotel.description
                rateTextView.text = hotel.rate.toString() + " / 10"
                priceTextView.text = "Rp. " + hotel.priceRange

                itemView.setOnClickListener {
                    val destination = ExploreFragmentDirections
                        .actionNavigationExploreToDetailFragment(hotel)
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