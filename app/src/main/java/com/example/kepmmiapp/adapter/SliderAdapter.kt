package com.example.kepmmiapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.remote.response.SliderResponse
import com.example.kepmmiapp.databinding.ItemSliderBinding

class SliderAdapter : ListAdapter<SliderResponse, SliderAdapter.SliderViewHolder>(diffCallback) {

    inner class SliderViewHolder(private var binding: ItemSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SliderResponse) {
            binding.apply {
                SliderIv.loadImage(item.gambar)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder =
        SliderViewHolder(
            ItemSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.d("SliderAdapter", "Binding slider at position $position")
    }

    private fun ImageView.loadImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.ic_refresh)
            .error(R.drawable.ic_broken_image)
            .into(this)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SliderResponse>() {
            override fun areItemsTheSame(
                oldItem: SliderResponse,
                newItem: SliderResponse
            ): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: SliderResponse,
                newItem: SliderResponse
            ): Boolean =
                oldItem == newItem


        }
    }
}