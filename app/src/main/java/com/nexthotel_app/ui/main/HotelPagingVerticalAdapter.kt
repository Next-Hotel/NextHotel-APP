package com.nexthotel_app.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexthotel_app.R
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import com.nexthotel_app.databinding.ItemVerticalBinding
import com.nexthotel_app.ui.main.home.HomeFragmentDirections

class HotelPagingVerticalAdapter :
    PagingDataAdapter<HotelSchema, HotelPagingVerticalAdapter.ViewHolder>(DIFF_CALLBACK) {
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


    inner class ViewHolder(private val binding: ItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: HotelSchema) {
            binding.apply {
                tvItemNameVertical.text = hotel.name
                tvItemRateVertical.text = hotel.rating
                tvItemCityVertical.text = hotel.city
                Glide.with(binding.root.context)
                    .load(hotel.imageUrl) // URL Gambar
                    .placeholder(R.drawable.ic_baseline_place_holder_24) // placeholder
                    .error(R.drawable.ic_baseline_broken_image_24) // while error
                    .fallback(R.drawable.ic_baseline_place_holder_24) // while null
                    .into(binding.ivItemImageVertical) // imageView mana yang akan diterapkan

                itemVerticalCard.setOnClickListener {
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
            ItemVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }
}
