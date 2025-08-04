package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName


data class PeriodeRekrutmenAnggotaResponseItem(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("tanggal_mulai")
    val tanggalMulai: String? = null,

    @field:SerializedName("tanggal_selesai")
    val tanggalSelesai: String? = null,

    @field:SerializedName("is_aktif")
    val isAktif: Boolean? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,
)
