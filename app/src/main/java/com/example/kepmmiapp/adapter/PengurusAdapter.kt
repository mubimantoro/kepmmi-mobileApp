package com.example.kepmmiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.remote.response.PengurusResponseItem
import com.example.kepmmiapp.databinding.ItemPengurusBinding

class PengurusAdapter :
    PagingDataAdapter<PengurusResponseItem, PengurusAdapter.PengurusViewHolder>(diffCallback) {


    inner class PengurusViewHolder(private val binding: ItemPengurusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PengurusResponseItem?) {

            item?.let {
                binding.apply {
                    namaLengkapTv.text = item.namaLengkap
                    jabatanTv.text = item.jabatan
                    fotoPengurusIv.loadImage(item.avatar)
                }
            }

        }
    }

    override fun onBindViewHolder(holder: PengurusAdapter.PengurusViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PengurusAdapter.PengurusViewHolder = PengurusViewHolder(
        ItemPengurusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .error(R.drawable.ic_broken_image)
            .into(this)

    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PengurusResponseItem>() {
            override fun areItemsTheSame(
                oldItem: PengurusResponseItem,
                newItem: PengurusResponseItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PengurusResponseItem,
                newItem: PengurusResponseItem
            ): Boolean = oldItem == newItem

        }
    }
}