package com.example.kepmmiapp.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.remote.response.PamfletResponseItem
import com.example.kepmmiapp.databinding.ItemPamfletBinding

class PamfletAdapter :
    PagingDataAdapter<PamfletResponseItem, PamfletAdapter.PamfletViewHolder>(diffCallback) {


    override fun onBindViewHolder(holder: PamfletAdapter.PamfletViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PamfletViewHolder(private val binding: ItemPamfletBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PamfletResponseItem) {
            binding.apply {
                pamfletIv.loadImage(item.gambar)
                captionTv.text = Html.fromHtml(item.caption, Html.FROM_HTML_MODE_LEGACY)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PamfletAdapter.PamfletViewHolder =
        PamfletViewHolder(
            ItemPamfletBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PamfletResponseItem>() {
            override fun areItemsTheSame(
                oldItem: PamfletResponseItem,
                newItem: PamfletResponseItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PamfletResponseItem,
                newItem: PamfletResponseItem
            ): Boolean = oldItem == newItem

        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .error(R.drawable.ic_broken_image)
            .into(this)
    }
}