package com.example.kepmmiapp.utils

data class PageMenu(
    val id: Int,
    val title: String,
    val destinationType: DestinationType
)

enum class DestinationType {
    TENTANG_ORGANISASI,
    PROGRAM_KERJA,
    PENGURUS
}