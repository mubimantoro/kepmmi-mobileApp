package com.example.kepmmiapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class PeriodeResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("tanggal_selesai")
	val tanggalSelesai: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("tanggal_mulai")
	val tanggalMulai: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("status")
	val status: Boolean
)
