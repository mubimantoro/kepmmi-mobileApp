package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName


data class PeriodeRekrutmenAnggotaResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("tanggal_mulai")
    val tanggalMulai: String,

    @field:SerializedName("tanggal_selesai")
    val tanggalSelesai: String,

    @field:SerializedName("is_aktif")
    val isAktif: Boolean,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,
)
