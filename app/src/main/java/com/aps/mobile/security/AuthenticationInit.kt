package com.aps.mobile.security

import android.content.Context
import com.aps.mobile.backend.AuthService
import com.aps.mobile.model.LoginUserPersistence
import com.aps.mobile.utils.RetrofitProvider
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationInit(val context: Context) {
    lateinit var authService: AuthService

    fun authenticationHandling() {
        authService =
            RetrofitProvider.createService(
                context,
                AuthService::class.java,
                okHttpClient()
            )

        authService.checkIfLoggedIn().enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable?) {
                LoginUserPersistence(context).clear()
                // todo: redirect to login
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                // all is well
            }
        })
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(context))
            .build()
    }
}
