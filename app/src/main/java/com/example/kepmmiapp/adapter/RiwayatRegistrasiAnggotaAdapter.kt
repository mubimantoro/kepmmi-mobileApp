package com.example.kepmmiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kepmmiapp.R
import com.example.kepmmiapp.data.remote.response.RegistrasiAnggotaResponseItem
import com.example.kepmmiapp.databinding.ItemRiwayatRegistrasiAnggotaBinding
import com.example.kepmmiapp.utils.DateFormatter

class RiwayatRegistrasiAnggotaAdapter(
) : ListAdapter<RegistrasiAnggotaResponseItem, RiwayatRegistrasiAnggotaAdapter.ViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRiwayatRegistrasiAnggotaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemRiwayatRegistrasiAnggotaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RegistrasiAnggotaResponseItem) {
            binding.apply {
                // Set periode name
                periodeNamaTv.text = item.periodeRekrutmenAnggota?.nama ?: "Periode Tidak Diketahui"

                // Set tanggal periode
                val startDate = item.periodeRekrutmenAnggota?.tanggalMulai?.let {
                    DateFormatter.formatDate(it)
                } ?: "N/A"
                val endDate = item.periodeRekrutmenAnggota?.tanggalSelesai?.let {
                    DateFormatter.formatDate(it)
                } ?: "N/A"
                periodeDateTv.text = "$startDate - $endDate"


                statusTv.text = item.status

                // Set status background
                val statusBg = when (item.status?.lowercase()) {
                    "pending" -> R.drawable.bg_status_pending
                    "diterima", "accepted" -> R.drawable.bg_status_accepted
                    "ditolak", "rejected" -> R.drawable.bg_status_rejected
                    "dibatalkan" -> R.drawable.bg_status_cancelled
                    else -> R.drawable.bg_status_default
                }
                statusTv.setBackgroundResource(statusBg)

                

            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<RegistrasiAnggotaResponseItem>() {
            override fun areItemsTheSame(
                oldItem: RegistrasiAnggotaResponseItem,
                newItem: RegistrasiAnggotaResponseItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: RegistrasiAnggotaResponseItem,
                newItem: RegistrasiAnggotaResponseItem
            ): Boolean =
                oldItem == newItem


        }
    }
}