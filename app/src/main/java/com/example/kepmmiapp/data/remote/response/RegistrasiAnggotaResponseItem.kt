package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName


data class RegistrasiAnggotaResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("periode_rekrutmen_anggota_id")
    val periodeRekrutmenAnggotaId: Int,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String

)
