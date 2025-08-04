package com.example.kepmmiapp.utils

data class PageMenu(
    val id: Int,
    val title: String,
    val destinationType: DestinationType
)

enum class DestinationType {
    PROFIL_ORGANISASI,
    PROGRAM_KERJA,
    STRUKTUR_ORGANISASI
}