package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class SliderResponseItem(

    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("gambar")
    val gambar: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,
)
