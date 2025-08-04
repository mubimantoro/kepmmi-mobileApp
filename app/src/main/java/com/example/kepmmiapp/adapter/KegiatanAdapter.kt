package com.example.kepmmiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.local.entity.KegiatanEntity
import com.example.kepmmiapp.databinding.ItemKegiatanBinding
import com.example.kepmmiapp.utils.DateFormatter

class KegiatanAdapter(
    private val onCLick: (kegiatan: KegiatanEntity) -> Unit
) : PagingDataAdapter<KegiatanEntity, KegiatanAdapter.KegiatanViewHolder>(diffCallback) {

    inner class KegiatanViewHolder(private val binding: ItemKegiatanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: KegiatanEntity?) {
            item?.let {
                with(binding) {
                    gambarKegiatanIv.loadImage(it.gambar)
                    judulTv.text = it.judul
                    categoryTv.text = it.kategori
                    authorDateTv.text =
                        """${it.author} - ${DateFormatter.formatDate(it.createdAt)}"""
                    itemView.setOnClickListener {
                        onCLick(item)
                    }

                }
            }
        }
    }

    override fun onBindViewHolder(holder: KegiatanViewHolder, position: Int) {
        val kegiatans = getItem(position)
        holder.bind(kegiatans)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KegiatanViewHolder =
        KegiatanViewHolder(
            ItemKegiatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.ic_broken_image)
            .into(this)
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<KegiatanEntity>() {
            override fun areItemsTheSame(
                oldItem: KegiatanEntity,
                newItem: KegiatanEntity
            ): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: KegiatanEntity,
                newItem: KegiatanEntity
            ): Boolean =
                oldItem == newItem

        }
    }



}