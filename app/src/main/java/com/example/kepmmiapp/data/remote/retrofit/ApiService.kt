package com.example.kepmmiapp.data.remote.retrofit

import com.example.kepmmiapp.data.remote.response.CommonResponse
import com.example.kepmmiapp.data.remote.response.KategoriResponse
import com.example.kepmmiapp.data.remote.response.KegiatanResponse
import com.example.kepmmiapp.data.remote.response.LoginResponse
import com.example.kepmmiapp.data.remote.response.PagingResponse
import com.example.kepmmiapp.data.remote.response.PamfletResponseItem
import com.example.kepmmiapp.data.remote.response.PeriodeRekrutmenResponse
import com.example.kepmmiapp.data.remote.response.ProfileData
import com.example.kepmmiapp.data.remote.response.ProfileResponse
import com.example.kepmmiapp.data.remote.response.ProgramKerjaResponseItem
import com.example.kepmmiapp.data.remote.response.RegisterResponse
import com.example.kepmmiapp.data.remote.response.RegistrasiAnggotaResponse
import com.example.kepmmiapp.data.remote.response.SliderResponse
import com.example.kepmmiapp.data.remote.response.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("nama_lengkap") namaLengkap: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("periode-rekrutmen")
    suspend fun getPeriodeRekrutmen(
        @Header("Authorization") jwtToken: String
    ): CommonResponse<PeriodeRekrutmenResponse>

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") jwtToken: String
    ): CommonResponse<UserResponse>

    @GET("public/sliders")
    suspend fun getSlider(): CommonResponse<List<SliderResponse>>



    @GET("public/pamflet")
    suspend fun getPamflet(
        @Query("page") page: Int,
    ): CommonResponse<PagingResponse<PamfletResponseItem>>

    @GET("public/categories")
    suspend fun getCategory(
        @Query("page") page: Int
    ): CommonResponse<PagingResponse<KategoriResponse>>

    @GET("public/kegiatan/{slug}")
    suspend fun getDetailKegiatan(
        @Path("slug") slug: String
    ): CommonResponse<KegiatanResponse>

    @POST("rekrutmen-anggota/daftar")
    suspend fun registrasiAnggota(
        @Header("Authorization") jwtToken: String
    ): CommonResponse<RegistrasiAnggotaResponse>

    @FormUrlEncoded
    @POST("profile/update")
    suspend fun updateProfil(
        @Header("Authorization") jwtToken: String,
        @Field("alamat") alamat: String,
        @Field("tempat_lahir") tempatLahir: String,
        @Field("tanggal_lahir") tanggalLahir: String,
        @Field("asal_kampus") asalKampus: String,
        @Field("jurusan") jurusan: String,
        @Field("angkatan_akademik") angkatanAkademik: String,
        @Field("asal_daerah") asalDaerah: String
    ): CommonResponse<RegistrasiAnggotaResponse>

    @GET("public/kegiatan")
    suspend fun getKegiatan(
        @Query("page") page: Int,
        @Query("search") search: String = ""
    ): CommonResponse<PagingResponse<KegiatanResponse>>

    @GET("public/program-kerja")
    suspend fun getProgramKerja(
        @Query("page") page: Int,
    ): CommonResponse<PagingResponse<ProgramKerjaResponseItem>>

}