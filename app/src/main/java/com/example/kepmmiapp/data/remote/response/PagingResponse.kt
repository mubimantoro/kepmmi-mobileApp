package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class PagingResponse<T>(


    @field:SerializedName("data")
    val data: List<T>,
)