package com.gonexwind.nexthotel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gonexwind.nexthotel.databinding.ItemVerticalBinding
import com.gonexwind.nexthotel.model.Hotel

class HotelVerticalAdapter(private val listHotel: List<Hotel>) :
    RecyclerView.Adapter<HotelVerticalAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Hotel)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val binding: ItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: Hotel) {
            val (_, name, city, imageUrl, rating, description, priceRange) = hotel
            binding.apply {
                imageView.load(imageUrl)
                nameTextView.text = name
                cityTextView.text = city
                rateTextView.text = rating
                descTextView.text = description
                priceTextView.text = priceRange

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listHotel[adapterPosition])
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
        val data = listHotel[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listHotel.size
}