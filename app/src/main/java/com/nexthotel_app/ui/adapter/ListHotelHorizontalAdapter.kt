package com.nexthotel_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexthotel_app.core.source.model.Hotel
import com.nexthotel_app.databinding.ItemHorizontalBinding
import com.nexthotel_app.ui.home.HomeFragmentDirections

class ListHotelHorizontalAdapter :
    PagingDataAdapter<Hotel, ListHotelHorizontalAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Hotel>() {
            override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


    inner class ViewHolder(private val binding: ItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: Hotel) {
            binding.apply {
                tvItemNameHorizontal.text = hotel.namaHotel
                tvItemRateHorizontal.text = hotel.rating
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
