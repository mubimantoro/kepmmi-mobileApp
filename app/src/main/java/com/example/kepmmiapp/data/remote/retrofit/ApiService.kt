package com.example.kepmmiapp.data.remote.retrofit

import com.example.kepmmiapp.data.remote.response.CategoryResponseItem
import com.example.kepmmiapp.data.remote.response.CommonResponse
import com.example.kepmmiapp.data.remote.response.KegiatanResponseItem
import com.example.kepmmiapp.data.remote.response.LoginResponseItem
import com.example.kepmmiapp.data.remote.response.PagingResponse
import com.example.kepmmiapp.data.remote.response.PamfletResponseItem
import com.example.kepmmiapp.data.remote.response.PeriodeRekrutmenAnggotaResponseItem
import com.example.kepmmiapp.data.remote.response.ProfilOrganisasiResponseItem
import com.example.kepmmiapp.data.remote.response.ProgramKerjaResponseItem
import com.example.kepmmiapp.data.remote.response.RegisterResponseItem
import com.example.kepmmiapp.data.remote.response.RegistrasiAnggotaResponseItem
import com.example.kepmmiapp.data.remote.response.SliderResponseItem
import com.example.kepmmiapp.data.remote.response.UserResponseItem
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("nama_lengkap") namaLengkap: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponseItem

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponseItem

    @GET("public/sliders")
    suspend fun getSlider(): CommonResponse<List<SliderResponseItem>>

    @GET("public/kegiatan-home")
    suspend fun getKegiatanHome(): CommonResponse<List<KegiatanResponseItem>>

    @GET("periode-rekrutmen/active")
    suspend fun getActivePeriode(
        @Header("Authorization") jwtToken: String
    ): CommonResponse<PeriodeRekrutmenAnggotaResponseItem>

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") jwtToken: String
    ): CommonResponse<UserResponseItem>

    @FormUrlEncoded
    @POST("profile/update")
    suspend fun updateProfile(
        @Header("Authorization") jwtToken: String,
        @FieldMap profileData: Map<String, String>
    ): CommonResponse<UserResponseItem>

    @POST("rekrutmen-anggota/daftar")
    suspend fun registrasiAnggota(
        @Header("Authorization") jwtToken: String
    ): CommonResponse<RegistrasiAnggotaResponseItem>

    @GET("public/pamflet")
    suspend fun getPamflet(
        @Query("page") page: Int,
    ): CommonResponse<PagingResponse<PamfletResponseItem>>

    @GET("public/categories")
    suspend fun getCategory(
        @Query("page") page: Int
    ): CommonResponse<PagingResponse<CategoryResponseItem>>

    @GET("public/kegiatan")
    suspend fun getKegiatan(
        @Query("page") page: Int,
    ): CommonResponse<PagingResponse<KegiatanResponseItem>>


    @GET("public/program-kerja")
    suspend fun getProgramKerja(
        @Query("page") page: Int,
    ): CommonResponse<PagingResponse<ProgramKerjaResponseItem>>

    @GET("public/profil-organisasi")
    suspend fun getProfilOrganisasi(): CommonResponse<List<ProfilOrganisasiResponseItem>>


}