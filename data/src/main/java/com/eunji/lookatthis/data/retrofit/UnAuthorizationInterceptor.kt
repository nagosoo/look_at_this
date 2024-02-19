package com.eunji.lookatthis.data.retrofit

import android.content.Context
import com.eunji.lookatthis.data.datasource.local.UserDataSourceLocal
import com.eunji.lookatthis.domain.GlobalNetworkResponseCodeFlow
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UnAuthorizationInterceptor @Inject constructor(
    private val userDataSourceLocal: UserDataSourceLocal,
    @ApplicationContext private val context: Context
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val newRequest = chain.proceed(chain.request())
        if (newRequest.code == 401) {
            runBlocking {
                userDataSourceLocal.deleteBasicToken()
            }
            GlobalNetworkResponseCodeFlow.setGlobalResponseCodeFlow(true)
        }
        return newRequest
    }
}
