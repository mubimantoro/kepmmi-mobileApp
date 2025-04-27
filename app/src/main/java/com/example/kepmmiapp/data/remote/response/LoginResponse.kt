package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("permissions")
    val permissions: List<String>,

    @field:SerializedName("user")
    val user: UserResponse,

    @field:SerializedName("token")
    val token: String
)