package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName


data class AnggotaResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("jenis_anggota_id")
    val jenisAnggotaId: Int,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("jenis_anggota")
    val jenisAnggota: JenisAnggotaResponseItem? = null
)