package com.aps.mobile.security.interceptors

import android.content.Context
import com.aps.mobile.core.utils.startActivityAsNewTask
import com.aps.mobile.security.domain.LoginUserPersistence
import com.aps.mobile.security.ui.LoginActivity
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthenticationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val loginUserPersistence = LoginUserPersistence(context)
        val user = loginUserPersistence.fetch()
        if (user.username.isEmpty() || user.password.isEmpty()) {
            return chain.proceed(chain.request())
        }
        val credentials = Credentials.basic(user.username, user.password)
        val authenticatedRequest =
            chain.request().newBuilder().header("Authorization", credentials).build()
        val response = chain.proceed(authenticatedRequest)
        if (response.code() == 401) {
            loginUserPersistence.clear()
            startActivityAsNewTask(context, LoginActivity::class.java)
        }
        return response
    }
}
