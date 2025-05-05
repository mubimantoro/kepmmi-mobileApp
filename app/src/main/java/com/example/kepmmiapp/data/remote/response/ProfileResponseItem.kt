package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("tempat_lahir")
    val tempatLahir: String? = null,

    @field:SerializedName("tanggal_lahir")
    val tanggalLahir: String? = null,

    @field:SerializedName("asal_kampus")
    val asalKampus: String? = null,

    @field:SerializedName("jurusan")
    val jurusan: String? = null,

    @field:SerializedName("angkatan_akademik")
    val angkatanAkademik: String? = null,

    @field:SerializedName("asal_daerah")
    val asalDaerah: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String
)
