package com.aps.mobile.utils

import android.content.Context
import com.aps.mobile.R
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The provider is a singleton
 * Services created by this provider are not
 */
object RetrofitProvider {
    private var retrofit: Retrofit? = null
    private var client: OkHttpClient? = null

    private fun getInstance(context: Context): Retrofit {
        if (retrofit != null) {
            return retrofit!!
        }
        synchronized(this) {
            return when {
                client == null -> {
                    throw IllegalStateException("Tried to instantiate Retrofit without the OkHttpClient instance")
                }
                retrofit != null -> {
                    retrofit!!
                }
                else -> {
                    val gson = GsonBuilder()
                        .setLenient()
                        .create()

                    retrofit = Retrofit.Builder()
                        .baseUrl(context.resources.getString(R.string.api_server_staging))
                        .client(client!!)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()

                    retrofit!!
                }
            }
        }
    }

    fun <T> createService(context: Context, service: Class<T>, client: OkHttpClient? = null): T {
        if (client != null) {
            this.client = client
        }
        if (this.client == null) {
            throw IllegalStateException("Pass OkHttpClient in the first call of createService")
        }
        return getInstance(
            context
        ).create(service)
    }
}
