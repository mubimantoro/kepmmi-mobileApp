package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class CommonResponse<T>(

    @field:SerializedName("data")
    val data: T,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)