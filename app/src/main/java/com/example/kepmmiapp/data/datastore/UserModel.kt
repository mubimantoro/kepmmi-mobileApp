package com.example.kepmmiapp.data.datastore

data class UserModel(
    val namaLengkap: String,
    val email: String,
    val jwtToken: String,
    val isLogin: Boolean
)