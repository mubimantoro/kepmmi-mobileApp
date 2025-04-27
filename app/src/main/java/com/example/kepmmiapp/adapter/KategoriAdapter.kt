package com.example.kepmmiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kepmmiapp.data.remote.response.KategoriResponse
import com.example.kepmmiapp.databinding.ItemKategoriBinding

class KategoriAdapter :
    PagingDataAdapter<KategoriResponse, KategoriAdapter.KategoriViewHolder>(diffCallback) {


    inner class KategoriViewHolder(private val binding: ItemKategoriBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KategoriResponse?) {
            item?.let {
                binding.KategoriTv.text = it.nama
            }
        }
    }

    override fun onBindViewHolder(holder: KategoriAdapter.KategoriViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KategoriAdapter.KategoriViewHolder = KategoriViewHolder(
        ItemKategoriBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<KategoriResponse>() {
            override fun areItemsTheSame(
                oldItem: KategoriResponse,
                newItem: KategoriResponse
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: KategoriResponse,
                newItem: KategoriResponse
            ): Boolean = oldItem == newItem

        }
    }

}