package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName


data class StrukturOrganisasiResponseItem(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("gambar")
    val gambar: String? = null
)
