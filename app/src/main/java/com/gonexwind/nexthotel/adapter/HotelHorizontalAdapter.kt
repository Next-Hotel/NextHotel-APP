package com.gonexwind.nexthotel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gonexwind.nexthotel.databinding.ItemHorizontalBinding
import com.gonexwind.nexthotel.model.Hotel

class HotelHorizontalAdapter(private val listHotel: List<Hotel>) :
    RecyclerView.Adapter<HotelHorizontalAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Hotel)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val binding: ItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: Hotel) {
            val (_, name, city, imageUrl, rating, _, priceRange) = hotel
            binding.apply {
                imageView.load(imageUrl)
                nameTextView.text = name
                cityTextView.text = city
                rateTextView.text = rating
                priceTextView.text = priceRange

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listHotel[adapterPosition])
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
        val data = listHotel[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listHotel.size
}