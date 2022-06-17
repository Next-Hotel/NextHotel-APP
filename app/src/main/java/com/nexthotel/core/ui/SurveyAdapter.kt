package com.nexthotel.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexthotel.databinding.ItemSurveyBinding

class SurveyAdapter(private val itemClickListener: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<String> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.bindItem(list[position])
                holder.binding.surveyButton.setOnClickListener {
                    itemClickListener.invoke(list[position])
                }
            }
        }
    }

    override fun getItemCount() = if (list.isEmpty()) 1 else list.size

    class ItemViewHolder(val binding: ItemSurveyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: String) {
            binding.surveyButton.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ItemSurveyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}