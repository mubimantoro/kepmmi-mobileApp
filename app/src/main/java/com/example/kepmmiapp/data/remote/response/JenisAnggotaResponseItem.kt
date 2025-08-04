package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class JenisAnggotaResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String
)