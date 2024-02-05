package com.eunji.lookatthis.data.retrofit

import com.eunji.lookatthis.data.datasource.local.UserDataSourceLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor @Inject constructor(
    private val userDataSourceLocal: UserDataSourceLocal
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val basicToken = runBlocking {
            println("currnet thraed name : ${Thread.currentThread().name}")
            userDataSourceLocal.getBasicToken().first()
        }
        val newRequest =
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Basic $basicToken")
                .build()
        return chain.proceed(newRequest)
    }
}