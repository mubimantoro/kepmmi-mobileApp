package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProgramKerjaResponseItem(
    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("bidang_id")
    val bidangId: Int,

    @field:SerializedName("bidang")
    val bidang: BidangOrganisasiResponseItem,

    @field:SerializedName("status")
    val status: String
)
