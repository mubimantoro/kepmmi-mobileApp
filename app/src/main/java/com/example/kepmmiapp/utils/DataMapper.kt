package com.example.kepmmiapp.utils

import com.example.kepmmiapp.data.local.entity.KegiatanEntity
import com.example.kepmmiapp.data.remote.response.KegiatanResponseItem

object DataMapper {

    fun mapKegiatanResponseItemToKegiatanEntity(input: List<KegiatanResponseItem>): List<KegiatanEntity> =
        input.map {
            KegiatanEntity(
                id = it.id,
                createdAt = it.createdAt,
                gambar = it.gambar,
                judul = it.judul,
                konten = it.konten,
                kategori = it.kategori?.nama ?: "-",
                author = it.user.namaLengkap,
                slug = it.slug
            )
        }
}