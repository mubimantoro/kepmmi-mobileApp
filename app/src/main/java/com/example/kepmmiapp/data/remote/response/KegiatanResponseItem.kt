package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class KegiatanResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("kategori_id")
    val kategoriId: Int,

    @field:SerializedName("judul")
    val judul: String,

    @field:SerializedName("gambar")
    val gambar: String,

    @field:SerializedName("konten")
    val konten: String,

    @field:SerializedName("slug")
    val slug: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("kategori")
    val kategori: CategoryResponseItem? = null,

    @field:SerializedName("user")
    val user: UserResponseItem,


    )
