package com.example.kepmmiapp.data.remote.retrofit

import com.example.kepmmiapp.data.remote.response.CommonResponse
import com.example.kepmmiapp.data.remote.response.KategoriResponse
import com.example.kepmmiapp.data.remote.response.KegiatanResponse
import com.example.kepmmiapp.data.remote.response.PagingResponse
import com.example.kepmmiapp.data.remote.response.SliderResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("public/sliders")
    suspend fun getSlider(): CommonResponse<List<SliderResponse>>

    @GET("public/kegiatan")
    suspend fun getKegiatan(
        @Query("page") page: Int,
        @Query("search") search: String = ""
    ): CommonResponse<PagingResponse<KegiatanResponse>>

    @GET("public/categories")
    suspend fun getCategory(
        @Query("page") page: Int
    ): CommonResponse<PagingResponse<KategoriResponse>>

    @GET("public/kegiatan/{slug}")
    suspend fun getDetailKegiatan(
        @Path("slug") slug: String
    ): CommonResponse<KegiatanResponse>
}