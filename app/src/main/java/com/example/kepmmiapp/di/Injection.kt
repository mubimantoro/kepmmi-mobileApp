package com.example.kepmmiapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.kepmmiapp.data.datastore.UserPreferences
import com.example.kepmmiapp.data.local.room.KepmmiDatabase
import com.example.kepmmiapp.data.remote.retrofit.ApiConfig
import com.example.kepmmiapp.data.remote.retrofit.ApiService
import com.example.kepmmiapp.data.repository.AuthRepository
import com.example.kepmmiapp.data.repository.KepmmiRepository

private const val USER_PREFERENCES = "user_preferences"

object Injection {
    fun provideKepmmiRepository(context: Context): KepmmiRepository {
        val userPreferences = provideUserPreferences(context)
        return KepmmiRepository.getInstance(
            provideApiService(userPreferences),
            provideDatabase(context),
            userPreferences
        )
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreferences = provideUserPreferences(context)
        return AuthRepository.getInstance(
            provideApiService(userPreferences),
            userPreferences
        )
    }

    private fun provideDatabase(context: Context): KepmmiDatabase =
        KepmmiDatabase.getInstance(context)

    private fun provideApiService(userPreferences: UserPreferences): ApiService =
        ApiConfig.getApiService(userPreferences)

    private fun provideUserPreferences(context: Context): UserPreferences =
        UserPreferences.getInstance(
            providePreferencesDataStore(context)
        )

    private fun providePreferencesDataStore(context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(produceFile = {
            context.preferencesDataStoreFile(USER_PREFERENCES)
        })
    }
}