package com.nexthotel.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nexthotel.R
import com.nexthotel.core.data.local.entity.HotelEntity
import com.nexthotel.databinding.ItemVerticalBinding

class SearchAdapter(private val onBookmarkClick: (HotelEntity) -> Unit) :
    ListAdapter<HotelEntity, SearchAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: HotelEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemVerticalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hotel = getItem(position)
        holder.bind(hotel)

        val bookmarkButton = holder.binding.bookmarkButton
        bookmarkButton.apply {
            if (hotel.isBookmarked) setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_bookmark_white)
            ) else setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_bookmark_border_white)
            )
            setOnClickListener { onBookmarkClick(hotel) }
        }
    }

    inner class MyViewHolder(val binding: ItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: HotelEntity) {
            val (_, name, city, imageUrl, rate, description, price, stars) = hotel
            binding.apply {
                val idrPrice = "IDR " + price
                imageView.load(imageUrl)
                nameTextView.text = name
                cityTextView.text = city
                rateTextView.text = StringBuilder(rate).append(" ⭐")
                ratingBar1.rating = stars.toFloat()
                descTextView.text = description
                priceTextView.text = idrPrice
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(hotel)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<HotelEntity> =
            object : DiffUtil.ItemCallback<HotelEntity>() {
                override fun areItemsTheSame(old: HotelEntity, new: HotelEntity): Boolean {
                    return old.id == new.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(old: HotelEntity, new: HotelEntity): Boolean {
                    return old == new
                }
            }
    }
}