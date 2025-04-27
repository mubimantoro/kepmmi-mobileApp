package com.example.kepmmiapp.data.remote.response

data class RegistrasiAnggotaRequest(
    val alamat: String,
    val tempatLahir: String,
    val tanggalLahir: String,
    val asalKampus: String,
    val jurusan: String,
    val angkatanAkademik: String,
    val asalDaerah: String
)
