package com.nexthotel_app.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexthotel_app.R
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import com.nexthotel_app.databinding.ItemHorizontalBinding
import com.nexthotel_app.ui.main.home.HomeFragmentDirections

class HotelPagingHorizontalAdapter :
    PagingDataAdapter<HotelSchema, HotelPagingHorizontalAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HotelSchema>() {
            override fun areItemsTheSame(oldItem: HotelSchema, newItem: HotelSchema): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HotelSchema, newItem: HotelSchema): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


    inner class ViewHolder(private val binding: ItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: HotelSchema) {
            Log.d("MASUK HOTEL HORIZONTAL", hotel.toString())
            binding.apply {
                tvItemNameHorizontal.text = hotel.name
                tvItemRateHorizontal.text = hotel.rating
                Glide.with(binding.root.context)
                    .load(hotel.imageUrl) // URL Gambar
                    .placeholder(R.drawable.ic_baseline_place_holder_24) // placeholder
                    .error(R.drawable.ic_baseline_broken_image_24) // while error
                    .fallback(R.drawable.ic_baseline_place_holder_24) // while null
                    .into(binding.ivItemImageHorizontal) // imageView mana yang akan diterapkan

                itemHorizontalCard.setOnClickListener {
                    val toDetail =
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                            hotel
                        )
                    it.findNavController().navigate(toDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }
}