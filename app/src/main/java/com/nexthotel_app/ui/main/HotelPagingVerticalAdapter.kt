package com.nexthotel_app.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexthotel_app.R
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import com.nexthotel_app.databinding.ItemVerticalBinding

class HotelPagingVerticalAdapter(private val itemClickListener: (HotelSchema) -> Unit) :
    PagingDataAdapter<HotelSchema, MyViewHolder>(
        COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.binding.root.setOnClickListener {
                itemClickListener.invoke(data)
            }
        }
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<HotelSchema>() {
            override fun areItemsTheSame(oldItem: HotelSchema, newItem: HotelSchema): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HotelSchema, newItem: HotelSchema): Boolean {
                return oldItem == newItem
            }
        }
    }
}


class MyViewHolder(val binding: ItemVerticalBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(hotel: HotelSchema) {
        binding.tvItemNameVertical.text = hotel.name
        binding.tvItemRateVertical.text = hotel.rating
        binding.tvItemCityVertical.text = hotel.city

        Glide.with(binding.root.context)
            .load(hotel.imageUrl) // URL Gambar
            .placeholder(R.drawable.ic_baseline_place_holder_24) // placeholder
            .error(R.drawable.ic_baseline_broken_image_24) // while error
            .fallback(R.drawable.ic_baseline_place_holder_24) // while null
            .into(binding.ivItemImageVertical) // imageView mana yang akan diterapkan

    }

    companion object {
        fun create(parent: ViewGroup): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_vertical, parent, false)

            val binding = ItemVerticalBinding.bind(view)

            return MyViewHolder(
                binding
            )
        }
    }
}