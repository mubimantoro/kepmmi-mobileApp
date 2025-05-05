package com.example.kepmmiapp.data.repository

import android.util.Log
import com.example.kepmmiapp.data.Result
import com.example.kepmmiapp.data.datastore.UserModel
import com.example.kepmmiapp.data.datastore.UserPreferences
import com.example.kepmmiapp.data.remote.response.LoginResponseItem
import com.example.kepmmiapp.data.remote.response.RegisterResponseItem
import com.example.kepmmiapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AuthRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {
    fun register(
        namaLengkap: String,
        email: String,
        password: String
    ): Flow<Result<RegisterResponseItem>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.register(namaLengkap, email, password)

            if (response.success) {
                emit(Result.Success(response))
            }

        } catch (e: Exception) {
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.toString()
                emit(Result.Error("Registrasi gagal: $errorBody"))
            } else {
                emit(Result.Error(e.toString()))
            }
        }
    }

    fun login(email: String, password: String): Flow<Result<LoginResponseItem>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            if (response.success) {
                val userData = UserModel(
                    namaLengkap = response.user.namaLengkap,
                    email = response.user.email,
                    jwtToken = response.token,
                    isLogin = true
                )
                saveSession(userData)
                Log.d("AuthRepository", "jwtToken: ${userData.jwtToken}")
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody().toString()
                emit(Result.Error("Login gagal: $errorBody"))
            } else {
                emit(Result.Error(e.toString()))
            }
        }
    }

    private suspend fun saveSession(userData: UserModel) {
        userPreferences.saveSession(userData)
    }

    fun getSession(): Flow<UserModel> = userPreferences.getSession()

    suspend fun logout() {
        userPreferences.logout()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences
        ): AuthRepository = instance ?: synchronized(this) {
            instance ?: AuthRepository(apiService, userPreferences)
        }.also { instance = it }
    }
}