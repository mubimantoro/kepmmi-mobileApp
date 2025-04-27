package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class BidangOrganisasiResponseItem(
    @field:SerializedName("tugas")
    val tugas: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int
)
