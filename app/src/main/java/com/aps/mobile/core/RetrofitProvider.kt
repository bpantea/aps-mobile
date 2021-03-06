package com.aps.mobile.core

import android.content.Context
import com.aps.mobile.R
import com.aps.mobile.security.interceptors.BasicAuthenticationInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


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
                        .addConverterFactory(NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()

                    retrofit!!
                }
            }
        }
    }

    fun <T> createService(context: Context, service: Class<T>): T {
        if (this.client == null) {
            throw IllegalStateException("Pass OkHttpClient in the first call of createService")
        }
        return getInstance(
            context
        ).create(service)
    }

    fun createInstance(context: Context) {
        this.client = okHttpClient(context)
        if (retrofit == null) {
            getInstance(context)
        }
    }

    private fun okHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                BasicAuthenticationInterceptor(
                    context
                )
            )
            .build()
    }
}

class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation?>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate: Converter<ResponseBody, *> =
            retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter<ResponseBody, Any> { body ->
            if (body.contentLength() == 0L) {
                null
            } else delegate.convert(body)
        }
    }
}
