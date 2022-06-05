package com.nexthotel_app.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexthotel_app.R
import com.nexthotel_app.data.local.hotel.hotel_entity.HotelSchema
import com.nexthotel_app.databinding.ItemVerticalBinding

class HotelExploreAdapter(private val itemClickListener: (HotelSchema) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<HotelSchema> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<HotelSchema>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> ItemViewHolder(
                ItemVerticalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> EmptyViewHolder(
                ItemVerticalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.bindItem(list[position])
                holder.binding.root.setOnClickListener {
                    itemClickListener.invoke(list[position])
                }
            }
            is EmptyViewHolder -> {
            }
        }
    }

    override fun getItemViewType(position: Int) = if (list.isEmpty()) 0 else 1

    override fun getItemCount() = if (list.isEmpty()) 1 else list.size

    class ItemViewHolder(val binding: ItemVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: HotelSchema) {
            binding.tvItemNameVertical.text = item.name
            binding.tvItemRateVertical.text = item.rating
            binding.tvItemCityVertical.text = item.city

            Glide.with(binding.root.context)
                .load(item.imageUrl) // URL Gambar
                .placeholder(R.drawable.ic_baseline_place_holder_24) // placeholder
                .error(R.drawable.ic_baseline_broken_image_24) // while error
                .fallback(R.drawable.ic_baseline_place_holder_24) // while null
                .into(binding.ivItemImageVertical) // imageView mana yang akan diterapkan

        }
    }

    class EmptyViewHolder(binding: ItemVerticalBinding) : RecyclerView.ViewHolder(binding.root)
}