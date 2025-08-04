package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RiwayatRegistrasiAnggotaResponseItem(

    @field:SerializedName("data")
    val data: List<RegistrasiAnggotaResponseItem>? = null,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)