package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName


data class CategoryResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("slug")
    val slug: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,
)
