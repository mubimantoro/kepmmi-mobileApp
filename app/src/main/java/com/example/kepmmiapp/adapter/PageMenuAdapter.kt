package com.example.kepmmiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kepmmiapp.databinding.ItemPageMenuBinding
import com.example.kepmmiapp.utils.PageMenu

class PageMenuAdapter(private val onItemClick: (PageMenu) -> Unit) :
    ListAdapter<PageMenu, PageMenuAdapter.PageMenuViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageMenuAdapter.PageMenuViewHolder {
        val binding = ItemPageMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PageMenuViewHolder(binding, onItemClick)
    }


    override fun onBindViewHolder(holder: PageMenuAdapter.PageMenuViewHolder, position: Int) {
holder.bind(getItem(position))
    }

    inner class PageMenuViewHolder(
        private val binding: ItemPageMenuBinding,
        private val onItemClick: (PageMenu) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PageMenu) {
            binding.apply {
                pageTitleTv.text = item.title

                itemView.setOnClickListener {
                    onItemClick(item)
                }

            }
        }

    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PageMenu>() {
            override fun areItemsTheSame(oldItem: PageMenu, newItem: PageMenu): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PageMenu, newItem: PageMenu): Boolean =
                oldItem == newItem
        }
    }
}