package com.example.kepmmiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.local.entity.KegiatanEntity
import com.example.kepmmiapp.databinding.ItemKegiatanBinding
import com.example.kepmmiapp.utils.DateFormatter

class KegiatanAdapter(
    private val onCLick: (kegiatan: KegiatanEntity) -> Unit
) : PagingDataAdapter<KegiatanEntity, KegiatanAdapter.KegiatanViewHolder>(diffCallback) {

    class KegiatanViewHolder(private val binding: ItemKegiatanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: KegiatanEntity?) {
            item?.let {
                with(binding) {
                    gambarKegiatanIv.load(it.gambar) {
                        crossfade(true)
                        crossfade(400)
                        error(R.drawable.ic_broken_image)
                        transformations(RoundedCornersTransformation(25f))
                    }
                    judulTv.text = it.judul
                    categoryTv.text = it.kategori
                    authorDateTv.text =
                        """${it.author} - ${DateFormatter.formatDate(it.createdAt)}"""

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