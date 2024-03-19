package com.eunji.lookatthis.data.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(
    private val authorizationInterceptor: AuthorizationInterceptor,
    private val unAuthorizationInterceptor: UnAuthorizationInterceptor
) {
    private val httpLoggingInterceptor: HttpLoggingInterceptor by lazy { HttpLoggingInterceptor() }

    private val contentType = "application/json".toMediaType()

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getRetrofitBuilder(baseurl: String, needAuthorization: Boolean): Retrofit {
        return Retrofit.Builder().baseUrl(baseurl)
            .client(getOkHttpClient(needAuthorization))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    private fun getOkHttpClient(needAuthorization: Boolean): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            interceptors().apply {
                add(httpLoggingInterceptor.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                if (needAuthorization) {
                    add(authorizationInterceptor)
                    add(unAuthorizationInterceptor)
                }
            }
        }.build()
    }

}