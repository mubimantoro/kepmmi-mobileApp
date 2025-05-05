package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfilOrganisasiResponseItem(

    @field:SerializedName("buku_saku")
    val bukuSaku: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("logo")
    val logo: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("pedoman_intern")
    val pedomanIntern: String? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("ringkasan")
    val ringkasan: String? = null
)
