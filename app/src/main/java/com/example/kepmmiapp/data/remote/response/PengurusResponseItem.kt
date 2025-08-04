package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName


data class PengurusResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("nama_lengkap")
    val namaLengkap: String,

    @field:SerializedName("jabatan")
    val jabatan: String,

    @field:SerializedName("avatar")
    val avatar: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String

)