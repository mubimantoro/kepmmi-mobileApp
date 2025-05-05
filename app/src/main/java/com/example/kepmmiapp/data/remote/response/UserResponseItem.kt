package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponseItem(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("nama_lengkap")
    val namaLengkap: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("avatar")
    val avatar: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: String? = null,

    @field:SerializedName("profile")
    val profile: ProfileResponseItem? = null,
)
