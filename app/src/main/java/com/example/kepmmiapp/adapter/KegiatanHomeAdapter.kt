package com.example.kepmmiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.remote.response.KegiatanResponseItem
import com.example.kepmmiapp.databinding.ItemKegiatanHomeBinding
import com.example.kepmmiapp.utils.DateFormatter

class KegiatanHomeAdapter(private val onItemClick: (KegiatanResponseItem) -> Unit) :
    ListAdapter<KegiatanResponseItem, KegiatanHomeAdapter.ListViewHolder>(diffCallback) {

    inner class ListViewHolder(private val binding: ItemKegiatanHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KegiatanResponseItem) {
            binding.apply {
                judulTv.text = item.judul
                categoryTv.text = item.kategori?.nama
                dateTv.text = DateFormatter.formatDate(item.createdAt)
                gambarKegiatanIv.loadImage(item.gambar)

                itemView.setOnClickListener {
                    onItemClick(item)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder =
        ListViewHolder(
            ItemKegiatanHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun ImageView.loadImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.ic_broken_image)
            .into(this)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<KegiatanResponseItem>() {
            override fun areItemsTheSame(
                oldItem: KegiatanResponseItem,
                newItem: KegiatanResponseItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: KegiatanResponseItem,
                newItem: KegiatanResponseItem
            ): Boolean = oldItem == newItem

        }
    }

}