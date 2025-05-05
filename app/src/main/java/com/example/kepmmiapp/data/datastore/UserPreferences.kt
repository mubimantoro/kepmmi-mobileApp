package com.example.kepmmiapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit {
            it[FULLNAME_KEY] = user.namaLengkap
            it[EMAIL_KEY] = user.email
            it[JWT_KEY] = user.jwtToken
            it[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map {
            UserModel(
                it[FULLNAME_KEY] ?: "",
                it[EMAIL_KEY] ?: "",
                it[JWT_KEY] ?: "",
                it[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        @Volatile
        private var instance: UserPreferences? = null
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val FULLNAME_KEY = stringPreferencesKey("fullname")
        private val JWT_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences =
            instance ?: synchronized(this) {
                instance ?: UserPreferences(dataStore)
            }.also { instance = it }

    }
}