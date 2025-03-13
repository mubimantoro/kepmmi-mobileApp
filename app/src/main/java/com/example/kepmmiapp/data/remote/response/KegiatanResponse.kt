package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName




data class KegiatanResponse(

    @field:SerializedName("kategori_id")
    val kategoriId: Int,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("konten")
    val konten: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("kategori")
    val kategori: KategoriResponse,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("judul")
    val judul: String,

    @field:SerializedName("gambar")
    val gambar: String,

    @field:SerializedName("user")
    val user: UserResponse,

    @field:SerializedName("slug")
    val slug: String
)
