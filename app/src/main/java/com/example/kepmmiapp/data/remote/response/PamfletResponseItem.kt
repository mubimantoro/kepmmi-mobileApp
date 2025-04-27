package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class PamfletResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("gambar")
    val gambar: String,

    @field:SerializedName("caption")
    val caption: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,
)