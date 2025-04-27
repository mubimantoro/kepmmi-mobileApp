package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileData(
    @field:SerializedName("user") val user: UserData,
    @field:SerializedName("profile") val profile: ProfileDetail?
)
