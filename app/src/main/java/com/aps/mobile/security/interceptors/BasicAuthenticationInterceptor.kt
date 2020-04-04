package com.aps.mobile.security.interceptors

import android.content.Context
import com.aps.mobile.security.domain.LoginUserPersistence
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthenticationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val user = LoginUserPersistence(context).fetch()
        val credentials = Credentials.basic(user.username, user.password)
        val authenticatedRequest =
            chain.request().newBuilder().header("Authorization", credentials).build()
        val response = chain.proceed(authenticatedRequest)

        return response
    }
}
