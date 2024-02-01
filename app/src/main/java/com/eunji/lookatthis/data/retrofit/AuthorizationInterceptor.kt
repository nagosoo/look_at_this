package com.eunji.lookatthis.data.retrofit

import com.eunji.lookatthis.data.datasource.local.UserDataSourceLocal
import com.eunji.lookatthis.data.repository.UserRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor @Inject constructor(
    private val userRepository: UserDataSourceLocal
) : Interceptor {

    private val base64UserAccount = userRepository.getBase64UserAccount()

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest =
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Basic $base64UserAccount")
                .build()
        return chain.proceed(newRequest)
    }
}