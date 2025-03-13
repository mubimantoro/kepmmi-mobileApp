package com.example.kepmmiapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "kegiatan")
@Parcelize
data class KegiatanEntity(
    @PrimaryKey
    @field:ColumnInfo("id")
    var id: Int = 0,

    @field:ColumnInfo("gambar")
    var gambar: String = "",

    @field:ColumnInfo("judul")
    var judul: String = "",

    @field:ColumnInfo("konten")
    var konten: String = "",

    @field:ColumnInfo("kategori")
    var kategori: String = "",

    @field:ColumnInfo("author")
    var author: String = "",

    @field:ColumnInfo("created_at")
    var createdAt: String = "",

    @field:ColumnInfo("slug")
    var slug: String = ""
) : Parcelable