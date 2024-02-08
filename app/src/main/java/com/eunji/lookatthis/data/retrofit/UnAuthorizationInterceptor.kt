package com.eunji.lookatthis.data.retrofit

import android.content.Context
import android.content.Intent
import com.eunji.lookatthis.data.datasource.local.UserDataSourceLocal
import com.eunji.lookatthis.presentation.view.entry.EntryActivity
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
//            runBlocking {
//                userDataSourceLocal.deleteBasicToken()
//            }
//            val intent = Intent(context, EntryActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//            this.context.startActivity(intent)
        }
        return newRequest
    }
}
