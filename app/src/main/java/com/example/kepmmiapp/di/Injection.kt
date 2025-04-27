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
import com.example.kepmmiapp.data.repository.KepmmiRepository

private const val USER_PREFERENCES = "user_preferences"

object Injection {
    fun provideRepository(context: Context): KepmmiRepository {
        return KepmmiRepository.getInstance(
            provideApiService(),
            provideDatabase(context),
            provideUserPreferences(context)
        )
    }

    private fun provideDatabase(context: Context): KepmmiDatabase =
        KepmmiDatabase.getInstance(context)

    private fun provideApiService(): ApiService = ApiConfig.getApiService()

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