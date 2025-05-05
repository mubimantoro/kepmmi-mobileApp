package com.example.kepmmiapp.data.interceptor

import com.example.kepmmiapp.data.datastore.UserPreferences
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.greenrobot.eventbus.EventBus

@OptIn(DelicateCoroutinesApi::class)
class AuthInterceptor(private val userPreferences: UserPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        if (response.code == 401) {
            runBlocking {
                userPreferences.logout()
            }

            GlobalScope.launch(Dispatchers.Main) {
                EventBus.getDefault().post(LogoutEvent())
            }
        }

        return response
    }
}

class LogoutEvent