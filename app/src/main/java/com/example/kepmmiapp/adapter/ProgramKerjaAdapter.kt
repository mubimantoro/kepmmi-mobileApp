package com.example.kepmmiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kepmmiapp.data.remote.response.ProgramKerjaResponseItem
import com.example.kepmmiapp.databinding.ItemProgramKerjaBinding

class ProgramKerjaAdapter :
    ListAdapter<ProgramKerjaResponseItem, ProgramKerjaAdapter.ViewHolder>(diffCallback) {


    override fun onBindViewHolder(holder: ProgramKerjaAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgramKerjaAdapter.ViewHolder {
        val binding =
            ItemProgramKerjaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(private val binding: ItemProgramKerjaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProgramKerjaResponseItem) {
            binding.apply {
                tvNamaProgramKerja.text = item.nama
                tvBidang.text = item.bidang.nama
                statusTv.text = item.status
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ProgramKerjaResponseItem>() {
            override fun areItemsTheSame(
                oldItem: ProgramKerjaResponseItem,
                newItem: ProgramKerjaResponseItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ProgramKerjaResponseItem,
                newItem: ProgramKerjaResponseItem
            ): Boolean =
                oldItem == newItem

        }

    }
}
