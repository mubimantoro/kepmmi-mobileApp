package com.example.kepmmiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kepmmiapp.data.remote.response.CategoryResponseItem
import com.example.kepmmiapp.databinding.ItemCategoryBinding

class CategoryAdapter :
    PagingDataAdapter<CategoryResponseItem, CategoryAdapter.CategoryViewHolder>(diffCallback) {

    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryResponseItem?) {
            item?.let {
                binding.categoryLabelTv.text = item.nama
            }

        }
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder = CategoryViewHolder(
        ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CategoryResponseItem>() {
            override fun areItemsTheSame(
                oldItem: CategoryResponseItem,
                newItem: CategoryResponseItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CategoryResponseItem,
                newItem: CategoryResponseItem
            ): Boolean = oldItem == newItem

        }
    }
}